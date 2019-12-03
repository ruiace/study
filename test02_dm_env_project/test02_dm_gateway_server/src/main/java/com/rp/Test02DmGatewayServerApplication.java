package com.rp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class Test02DmGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test02DmGatewayServerApplication.class, args);
    }

}
