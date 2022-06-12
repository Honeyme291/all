package com.project.all.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //shiroFilterBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //設置安全管理器
        //添加shiro的过滤器。
        /*
        * anon 无需认证就可访问
        * authc 必须认证才能访问
        * user 必须拥有 记住我 功能才能用
        * perms 拥有摸个资源的权限才能够使用
        * role 用某个角色权限才能够使用
        *
        * */
        Map<String,String> filterChainDefinitionMap=new LinkedHashMap<>();

        filterChainDefinitionMap.put("/user/add","perms[user:add]");

        filterChainDefinitionMap.put("/user/update","perms[user:update]");
        filterChainDefinitionMap.put("/chat","anon");
        filterChainDefinitionMap.put("/user/sender","anon");
        filterChainDefinitionMap.put("/user/*","authc");


        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        bean.setSecurityManager(defaultWebSecurityManager);

        //设置登录的请求。
        bean.setLoginUrl("/user/login");
        //进入未授权的页面

        bean.setUnauthorizedUrl("/authentic");
        return bean;
    }

    //DefaultWebSecurityManager
    @Bean("securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserEmailRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //让他去关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    //创建realm,需要自定义
    @Bean
    public UserEmailRealm userRealm(){
        return new UserEmailRealm();
    }
}
