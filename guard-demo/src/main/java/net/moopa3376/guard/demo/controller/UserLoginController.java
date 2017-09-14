package net.moopa3376.guard.demo.controller;


import net.moopa3376.guard.Guard;
import net.moopa3376.guard.demo.ResponseCode;
import net.moopa3376.guard.demo.WebReturnResultMessage;
import net.moopa3376.guard.token.AuthorizedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Moopa on 03/05/2017.
 * blog: moopa.net
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

        String loginname = httpServletRequest.getParameter("name");
        String password  = httpServletRequest.getParameter("password");

        if(loginname == null){
            return WebReturnResultMessage.ifParamLost("name",loginname).toString();
        }
        if(password == null){
            return WebReturnResultMessage.ifParamLost("password",password).toString();
        }

        AuthorizedToken authorizedToken = Guard.login(loginname,password,httpServletResponse);
        if(authorizedToken == null){                                                                                             //非法account
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
