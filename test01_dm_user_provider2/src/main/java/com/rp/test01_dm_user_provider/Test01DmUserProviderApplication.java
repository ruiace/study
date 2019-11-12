package com.rp.test01_dm_user_provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Test01DmUserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test01DmUserProviderApplication.class, args);
    }

}
