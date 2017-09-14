package net.moopa3376.guard.demo.controller;

import net.moopa3376.guard.demo.ResponseCode;
import net.moopa3376.guard.demo.WebReturnResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Moopa on 19/05/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
@Controller
public class TestGuardController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    //Services
    @RequestMapping(value = "admin/info",method = RequestMethod.GET)
    @ResponseBody
    public String userLogin(){
        WebReturnResultMessage webReturnResultMessage = null;

        webReturnResultMessage = new WebReturnResultMessage();
        webReturnResultMessage.setResponseCode(ResponseCode.OK);
        webReturnResultMessage.setMessage("\"login succeed.\"");

        return webReturnResultMessage.toString();
    }

    @RequestMapping(value = "user/info",method = RequestMethod.GET)
    @ResponseBody
    public String getUserInfo(){
        WebReturnResultMessage webReturnResultMessage = null;

        webReturnResultMessage = new WebReturnResultMessage();
        webReturnResultMessage.setResponseCode(ResponseCode.OK);
        webReturnResultMessage.setMessage("\"login succeed.\"");

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
