package com.rp.test01_dm_eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Test01DmEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test01DmEurekaServerApplication.class, args);
    }

}
