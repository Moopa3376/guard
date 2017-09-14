package net.moopa3376.guard.checker;

import net.moopa3376.guard.Guard;
import net.moopa3376.guard.jwt.JwtWrapper;
import net.moopa3376.guard.model.account.Account;
import net.moopa3376.guard.service.IGuardService;
import net.moopa3376.guard.token.AuthorizedToken;

/**
 * Created by Moopa on 16/05/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class LogInChecker {
    public static IGuardService guardService = Guard.guardService;

    /**
     * 用来进行登录校验操作,主要流程:
     * 1.通过userMgntService来找到存储在数据库中的database
     * 2.通过用户自定义的userMgntService来进行密码校验
     * @param name
     * @param password
     * @return null - 用户名不存在或密码不符合  not null - 登录成功
     */
    public AuthorizedToken signin(String name, String password){
        AuthorizedToken authorizedToken = null;

        //接下来比对loginname和password是否匹配
        //需要拿到数据库中存储的密码
        if(guardService == null){
            guardService = Guard.guardService;
        }
        Account account = guardService.getAccountByName(name);
        //用户名不存在
        if(account == null){
            return null;
        }
        String anpassword = account.getPassword();
        boolean match = guardService.matchPassword(password,anpassword);

        if(match){
            authorizedToken = new AuthorizedToken();
            //开始在authorizeToken中加入一些登录信息
            authorizedToken.setName(name);
            //加入token
            authorizedToken.updateJwtToken(JwtWrapper.getJwt(System.currentTimeMillis()+""));

        }

        return authorizedToken;
    }




}
