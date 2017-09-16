package net.moopa3376.guard;

import java.util.Map;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.moopa3376.guard.api.Api;
import net.moopa3376.guard.api.ApiCheckResult;
import net.moopa3376.guard.api.ApiInfoMngt;
import net.moopa3376.guard.cache.GuardCacheOp;
import net.moopa3376.guard.checker.PermissionChecker;
import net.moopa3376.guard.checker.LogInChecker;
import net.moopa3376.guard.config.GuardConfigs;
import net.moopa3376.guard.exception.GuardError;
import net.moopa3376.guard.exception.GuardException;
import net.moopa3376.guard.http.HttpRequestMethod;
import net.moopa3376.guard.http.HttpRequestParameter;
import net.moopa3376.guard.jwt.JwtWrapper;
import net.moopa3376.guard.model.account.Account;
import net.moopa3376.guard.model.role.Role;
import net.moopa3376.guard.service.IGuardService;
import net.moopa3376.guard.token.AuthorizedToken;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Moopa on 13/05/2017.
 * blog: moopa.net
 *
 * 该类主要就只是一个门户,将一些操作统一集成到这个类中,主要操作有:
 * 1.登录
 * 2.下线
 * 3.TODO:加密
 * 4.TODO:解密
 *
 * 还有一些全局的变量也被存放在这个类中
 * 全局的服务guardService也放在这个类中
 * @autuor : Moopa
 */
public class Guard {
    private static Logger logger = LoggerFactory.getLogger(Guard.class);

    public static volatile boolean isInited = false;
    public static IGuardService guardService = null;

    protected static LogInChecker logInChecker = new LogInChecker();
    protected static PermissionChecker permissionChecker = new PermissionChecker();



    public static void init(){
        if(!isInited){
            if(!GuardConfigs.init()){
                return;
            }

            //获取用户自身所定义的服务实现类
            String serviceClass = GuardConfigs.get("guard.guardService");
            Class servClass = null;
            try {
                servClass = Class.forName(serviceClass.trim());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                guardService = (IGuardService) (servClass.newInstance());
                logger.info("--------GuardService initialize successfully--------");
            } catch (InstantiationException e) {
                System.err.println("Please add the constructor with no parameter.\n");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.err.println("Please add the public constructor with no parameter.\n");
                e.printStackTrace();
            }




            // init api http parameter info management
            ApiInfoMngt.init();



            GuardCacheOp.init();

            //初始化结束
            isInited = true;
        }
    }




    /**
     * 该方法用来进行登录操作,登录成功后返回
     * 交给SignInChecker来进行登录
     * @return null-登录不成功
     */
    public static AuthorizedToken login(String name, String password, ServletResponse servletResponse){
        AuthorizedToken authorizedToken = logInChecker.signin(name,password);

        if(authorizedToken != null) {
            //往http header中添加X-token
            ((HttpServletResponse) servletResponse).setHeader("X-token", authorizedToken.getJwtToken());
            //往cache中添加相关项
            GuardCacheOp.putToken(authorizedToken);
        }

        throw new GuardException(GuardError.LOGIN_FAIL,"check carefully and login again");
    }


    /**
     * 该方法主要用于用户的下线操作
     * 根据token来进行下线
     */
    public static boolean signoff(String tokenName){
        //直接在缓存中删除相应authorizeToken
        GuardCacheOp.removeToken(tokenName);
        //不删除account缓存

        return true;
    }

    public static AuthorizedToken updateAuthorizeToken(AuthorizedToken token, String key, String val, HttpServletResponse httpServletResponse){
        String jwt = token.getJwtToken();
        DecodedJWT decodedJWT = JwtWrapper.verifyAndDecodeJwt(jwt);
        String payload = new String(Base64.decodeBase64(decodedJWT.getPayload()));

        //进行json解析
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(payload);
        jsonObject.addProperty(key,val);

        String res_token = JwtWrapper.getJwt(jsonObject);

        //接下来在http-header中进行更新
        httpServletResponse.setHeader("X-token",res_token);

        //接下来cache中也要更新
        GuardCacheOp.removeToken(jwt);
        token.updateJwtToken(res_token);
        GuardCacheOp.putToken(token);

        return token;
    }


    public static boolean permissionCheck(String tokenname,String permission){

        Account account = GuardCacheOp.getAccount(tokenname);

        Role role = GuardCacheOp.getRoleById(account.getRoleId());

        return permissionChecker.permissionCheck(role,permission);
    }

    //导入新的角色 - 可能是用户自己创建的,只是在缓存中加入
    public void addRole(Role role){
        GuardCacheOp.putRole(role);
    }

    public static boolean isExcludeUrl(String permission){
        return guardService.isExcludeUrl(permission);
    }


    public static AuthorizedToken getAuthorizedToken(ServletRequest servletRequest){
        return GuardCacheOp.getToken(((HttpServletRequest)servletRequest).getHeader("X-token"));
    }

    /**
     * api check part
     * @param servletRequest
     * @return
     */
    public static ApiCheckResult check(ServletRequest servletRequest){
        //获取path和method
        String path = ((HttpServletRequest)servletRequest).getPathInfo();
        String method = ((HttpServletRequest) servletRequest).getMethod();

        //从api缓存中获取相应api
        Api api = ApiInfoMngt.getApi(path, HttpRequestMethod.get(method));
        if(api == null){
            return new ApiCheckResult(true,null,null,null);
        }

        /** 开始对参数进行比较验证 **/
        for(Map.Entry<String,HttpRequestParameter> p : api.getParams().entrySet()){
            String p_value = servletRequest.getParameter(p.getKey());
            //是否存在
            if(p_value == null && p.getValue().isRequired()){
                return new ApiCheckResult(false,p.getKey(), GuardError.API_PARAM_REQUIRED,GuardError.API_PARAM_REQUIRED.getMessage());
            }

            //进行pattern验证
            if(!p.getValue().getPattern().matcher(p_value).matches()){
                return new ApiCheckResult(false,p.getKey(),GuardError.API_PARAM_NOT_MATCH,GuardError.API_PARAM_NOT_MATCH.getMessage());
            }
        }

        return new ApiCheckResult(true,null,null,null);
    }






}
