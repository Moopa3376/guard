package net.moopa.guard.fliter;

import net.moopa.guard.config.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Moopa on 18/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class GuardFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(GuardFilter.class);
    private static boolean logger_output = "true".equals(Configs.get("filter.output")) ? true : false ;

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("-------------- Guard Filter start. --------------");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取请求路径
        String requestPath = ((HttpServletRequest)servletRequest).getRequestURI();
        if(logger_output){
            logger.info("Filter request path : {}",requestPath);
        }

        //获取相应token
        String token = ((HttpServletRequest) servletRequest).getHeader("X-token");
        //验证token正确性 - 是否是我方所签发的
        //=============

        //首先通过token获取相应account,验证相应权限



    }

    public void destroy() {

    }
}
