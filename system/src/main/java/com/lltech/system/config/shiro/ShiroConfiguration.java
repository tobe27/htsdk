package com.lltech.system.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.*;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 配置Shiro
 * @author Created by L.C.Y on 2018-11-26
 */
@Configuration
public class ShiroConfiguration {

    @Resource
    private ShiroFilter shiroFilter;

    @Resource
    private ShiroRealm shiroRealm;

    private static Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Bean
    SessionManager sessionManager() { // 禁用Shiro会话
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    @Bean
    SubjectDAO subjectDAO() { // 解决Shiro报错
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return subjectDAO;
    }

    @Bean
    SessionStorageEvaluator sessionStorageEvaluator() { // 禁用session存储
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(true);
        return sessionStorageEvaluator;
    }

    @Bean
    SecurityManager securityManager() { // 设置Shiro安全配置
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置shiroRealm
        securityManager.setRealm(shiroRealm);
        // 解决Shiro报错
        securityManager.setSubjectDAO(subjectDAO());
        // 配置sessionManager
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    void setSecurityManager() {
        SecurityUtils.setSecurityManager(securityManager());
        // 保证实现了Shiro内部lifecycle函数的bean执行
        new LifecycleBeanPostProcessor();
    }

// 此处配置会导致Shiro进行多次授权，不要使用
//    @Bean
//    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() { // 开启代理，用于注解，使用cglib方式为创建代理对象
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        return defaultAdvisorAutoProxyCreator;
//    }

    @Bean
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() { // 开启注解
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 配置自定义的过滤规则
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("shiroFilter", shiroFilter); // 配置无状态Filter，通过token验证
        shiroFilterFactoryBean.setFilters(filterMap);

        // 配置过滤链
        // 对顺序有要求，从上到下进行校验，需使用 LinkedHashMap
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/", "anon");
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/**", "shiroFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        return shiroFilterFactoryBean;
    }



}
