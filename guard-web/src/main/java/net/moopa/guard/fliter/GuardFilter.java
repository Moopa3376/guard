package net.moopa.guard.fliter;

import net.moopa.guard.Guard;
import net.moopa.guard.config.Configs;
import net.moopa.guard.model.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Moopa on 18/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class GuardFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(GuardFilter.class);
    private static boolean logger_output = "true".equals(Configs.get("filter.output")) ? true : false ;
    private static HashSet<String> exurls = new HashSet<String>();

    static {
        String exclude = Configs.get("guard.exclude.url");
        String[] ss = exclude.split(",");
        for(String s : ss){
            if(s != null && !s.equals("")){
                exurls.add(s);
            }else {
                logger.error("Error config : guard.exclude.url - {}",exclude);
            }
        }
    }


    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("-------------- Guard Filter start. --------------");

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //path转化
        String rp = Guard.guardService.convertRequestToPermission(servletRequest);
        System.out.println(rp);
        //忽略excludeurl
        if(exurls.contains(rp)){
            filterChain.doFilter(servletRequest,servletResponse);
        }


        //获取请求路径
        String requestPath = ((HttpServletRequest)servletRequest).getPathInfo();
        if(logger_output){
            logger.info("Filter request path : {}",requestPath);
        }

        //获取相应token
        String token = ((HttpServletRequest) servletRequest).getHeader("X-token");
        if(token == null){
            token = "null";
        }
        //验证token正确性 - 是否是我方所签发的,如果不正确则直接返回401
        //=============

        //看看cache中是否有当前的token
        if(!Guard.isTokenExisted(token)){
            //未登录 直接返回
            ((HttpServletResponse)servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        //验证相应权限
        boolean result = Guard.permissionCheck(token,rp);

        if(result){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            //未授权 直接返回
            ((HttpServletResponse)servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
    }

    public void destroy() {
        logger.info("-------------- Guard Filter end. --------------");
    }


}
