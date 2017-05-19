package net.moopa.guard.checker;

import net.moopa.guard.Guard;
import net.moopa.guard.service.IGuardService;
import net.moopa.guard.token.AuthorizeToken;

/**
 * Created by Moopa on 16/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class SignInChecker {
    public static IGuardService guardService = Guard.guardService;

    /**
     * 用来进行登录校验操作,主要流程:
     * 1.通过userMgntService来找到存储在数据库中的database
     * 2.通过用户自定义的userMgntService来进行密码校验
     * @param loginname
     * @param password
     * @return null - 用户名不存在或密码不符合  not null - 登录成功
     */
    public AuthorizeToken signin(String loginname,String password){
        AuthorizeToken authorizeToken = null;

        //接下来比对loginname和password是否匹配
        //需要拿到数据库中存储的密码
        String anpassword = guardService.getAccountByLoginname(loginname).getPassword();
        boolean match = guardService.matchPassword(password,anpassword);

        if(match){
            authorizeToken = new AuthorizeToken();
            //开始在authorizeToken中加入一些登录信息
            authorizeToken.setLoginname(loginname);
            authorizeToken.updateJwtToken(null);

            //往cache添加相关项
        }

        return authorizeToken;
    }




}
