package net.moopa3376.guard.fliter;

import com.auth0.jwt.interfaces.DecodedJWT;
import net.moopa3376.guard.api.ApiCheckResult;
import net.moopa3376.guard.Guard;
import net.moopa3376.guard.config.GuardConfigs;
import net.moopa3376.guard.exception.GuardError;
import net.moopa3376.guard.exception.GuardException;
import net.moopa3376.guard.http.HttpRequestMethod;
import net.moopa3376.guard.jwt.JwtWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Moopa on 18/05/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class GuardFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(GuardFilter.class);
    private static boolean logger_output = true;
    private static HashSet<String> exurls = new HashSet<String>();


    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("-------------- Guard Filter start. --------------");
        Guard.init();
        logger_output = "true".equals(GuardConfigs.get("filter.output")) ? true : false ;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //path转化
        String rp = Guard.guardService.convertRequestToPermission(servletRequest);


        //忽略excludeurl
        //用户自己去判断是否需要被过滤
        if(Guard.isExcludeUrl(rp)){
            // start to check http parameters
            ApiCheckResult checkResult = Guard.check(servletRequest);

            if(checkResult.isPassed()){
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }else{
                Guard.guardService.errorHandle(servletResponse,Guard.guardService.apiNameDefinetion(((HttpServletRequest)servletRequest).getPathInfo(),HttpRequestMethod.get(((HttpServletRequest)servletRequest).getMethod())),checkResult,logger);
                return;
            }
        }


        //获取请求路径
        if(logger_output){
            String requestPath = ((HttpServletRequest)servletRequest).getPathInfo();
            logger.info("Filter request path : {}",requestPath);
        }

        //获取相应token
        String token = ((HttpServletRequest) servletRequest).getHeader("X-token");
        if(token == null){
            throw new GuardException(GuardError.UNAUTHORIZED,"login");
        }

        if(logger_output){
            logger.debug("get request with token : {} , rp : {}",token,rp);
        }


        //验证token正确性 - 是否是我方所签发的,如果不正确则直接返回401
        DecodedJWT jwt = JwtWrapper.verifyAndDecodeJwt(token);
        if(jwt == null){
            throw new GuardException(GuardError.UNAUTHORIZED,"login");
        }

        //为了防止token被别人知晓后,别人拿了这个token伪造访问请求 - 暂时不做

        //验证相应权限
        boolean result = Guard.permissionCheck(token,rp);

        if(result){
            // start to check http parameters
            ApiCheckResult checkResult = Guard.check(servletRequest);

            if(checkResult.isPassed()){
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }else{
                Guard.guardService.errorHandle(servletResponse,Guard.guardService.apiNameDefinetion(((HttpServletRequest)servletRequest).getPathInfo(),HttpRequestMethod.get(((HttpServletRequest)servletRequest).getMethod())),checkResult,logger);
                return;
            }
        }else {
            //未授权 直接返回
            logger.warn("account unauthorized, token:{} , rp:{}", token, rp);
            throw new GuardException(GuardError.FORBIDDEN,"no solution");
        }


    }

    public void destroy() {
        logger.info("-------------- Guard Filter end. --------------");
    }


}
