package com.rp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Test02DmEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test02DmEurekaServerApplication.class, args);
    }

}
