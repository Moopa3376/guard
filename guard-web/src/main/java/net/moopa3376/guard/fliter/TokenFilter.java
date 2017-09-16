package net.moopa3376.guard.fliter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.moopa3376.guard.config.GuardConfigs;
import net.moopa3376.guard.jwt.JwtWrapper;

/**
 * Created by Moopa on 17/07/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class TokenFilter implements Filter{
    public static long expireIntervalTime = 12000;
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 该方法主要是针对即将过期的jwt token进行更新
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);

        //首先获取token
        String token = ((HttpServletRequest)servletRequest).getHeader("X-token");

        //token == null 表示该请求并不需要登录 - 因为前面已经有guard在过滤了
        if(token == null){
            return;
        }

        long current = System.currentTimeMillis();
        String expire = JwtWrapper.getValInPayload(token,"expire");
        long expire_l = Long.valueOf(expire);

        if(expire_l - current <= expireIntervalTime){
            //更新token
            String res = JwtWrapper.updateJwt(token,expire, String.valueOf(current+Long.valueOf(GuardConfigs.get("expire_time"))));

            //在response中更新token
            ((HttpServletResponse)servletResponse).setHeader("X-token",res);
        }
    }

    public void destroy() {

    }
}
