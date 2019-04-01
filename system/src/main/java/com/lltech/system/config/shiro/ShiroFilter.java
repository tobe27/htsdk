package com.lltech.system.config.shiro;

import com.lltech.common.utils.JwtUtils;
import com.lltech.common.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义Shiro过滤器，所有请求的请求头需携带token
 * @author Created by L.C.Y on 2018-8-28
 */
@Component
public class ShiroFilter extends AccessControlFilter {


    private Logger log = LoggerFactory.getLogger(ShiroFilter.class);

    /**
     * 关闭注册，使Shiro默认的anon生效
     * @param shiroFilter 自定义的
     * @return registrationBean
     */
    @Bean
    public FilterRegistrationBean registrationBean(ShiroFilter shiroFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(shiroFilter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    /**
     *返回false
     * @param servletRequest req
     * @param servletResponse res
     * @param o object
     * @return 返回结果是false的时候才会执行下面的onAccessDenied方法
     * @exception Exception e
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * 从请求头获取token并验证，验证通过后交给realm进行登录
     * @param servletRequest req
     * @param servletResponse res
     * @return 返回结果为true时，表明登录认证通过，执行controller层
     * @throws Exception e
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = getRequestToken(request);

        // token不存在时表示未登录，需重新登录
        if (token == null) {
            servletResponse.setContentType("application/json;charset=UTF-8");
            servletResponse.getWriter().print("{\"code\":300,\"message\":\"用户未登录，请重新登录！\"}");
            return false;
        }

        // token验证失败后，提醒重新登录
        if (!JwtUtils.validateTokenIgnoreExp(token)) {
            servletResponse.setContentType("application/json;charset=UTF-8");
            servletResponse.getWriter().print("{\"code\":300,\"message\":\"token校验失败，请重新登录！\"}");
            return false;
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(token, token);
        subject.login(usernamePasswordToken);
        return true;
    }


    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }

        return token;
    }

}
