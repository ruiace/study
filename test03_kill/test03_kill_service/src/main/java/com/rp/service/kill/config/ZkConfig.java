package com.rp.service.kill.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ZkConfig {


    @Autowired
    private Environment env;

    /**
     * 自定义zk客户端操作实例
     * @return
     */
    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(env.getProperty("zk.host"))
                .namespace(env.getProperty("zk.namespace"))
                //重试策略
                .retryPolicy(new RetryNTimes(3,1000))
                .build();
        cf.start();
        return cf;
    }
}
