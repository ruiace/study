package com.rp.service.kill.config;

import com.rp.service.kill.service.impl.CustomerRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public CustomerRealm customerRealm(){
        return new CustomerRealm();
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager dm = new DefaultWebSecurityManager();
        dm.setRealm(customerRealm());
        return dm;

    }



    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){

        ShiroFilterFactoryBean sfb = new ShiroFilterFactoryBean();
        sfb.setSecurityManager(securityManager());
        sfb.setLoginUrl("/to/login");
        sfb.setUnauthorizedUrl("/unauth");


        Map<String, String> map = new HashMap<>();
        map.put("/to/login","anon");
        map.put("/**","anon");
        map.put("/item/detail/*","authc");
        map.put("/kill/execute/*","authc");
        sfb.setFilterChainDefinitionMap(map);
        return  sfb;
    }


}
