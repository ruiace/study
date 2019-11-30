package com.rp.service.kill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.rp.service.kill.mapper")
//@EnableScheduling
public class KillApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(KillApplication.class,args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KillApplication.class);
    }
}
