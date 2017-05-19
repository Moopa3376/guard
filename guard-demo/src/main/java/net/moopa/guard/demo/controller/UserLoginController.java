package net.moopa.guard.demo.controller;


import net.moopa.guard.Guard;
import net.moopa.guard.demo.ResponseCode;
import net.moopa.guard.demo.WebReturnResultMessage;
import net.moopa.guard.model.account.Account;
import net.moopa.guard.token.AuthorizeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Moopa on 03/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
@Controller
public class UserLoginController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    //Services
    @RequestMapping(value = "user",method = RequestMethod.GET)
    @ResponseBody
    public String userLogin(){
        WebReturnResultMessage webReturnResultMessage = null;

        String loginname = httpServletRequest.getParameter("loginname");
        String password  = httpServletRequest.getParameter("password");

        if(loginname == null){
            return WebReturnResultMessage.ifParamLost("loginname",loginname).toString();
        }
        if(password == null){
            return WebReturnResultMessage.ifParamLost("password",password).toString();
        }

        AuthorizeToken authorizeToken = Guard.signin(loginname,password,httpServletResponse);
        if(authorizeToken == null){                                                                                             //非法account
            webReturnResultMessage = new WebReturnResultMessage();
            webReturnResultMessage.setResponseCode(ResponseCode.FORBIDDEN);
            webReturnResultMessage.setMessage("\"wrong password.\"");
            return webReturnResultMessage.toString();
        }else{
            webReturnResultMessage = new WebReturnResultMessage();
            webReturnResultMessage.setResponseCode(ResponseCode.OK);
            webReturnResultMessage.setMessage("\"login succeed.\"");
        }



        return webReturnResultMessage.toString();
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }


}
