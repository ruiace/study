package com.rp.test01_dm_user_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Test01DmUserConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test01DmUserConsumerApplication.class, args);
    }

}
