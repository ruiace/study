package com.rp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages={"com.rp.dao"})
public class Test04Mybatisplus1Application {

    public static void main(String[] args) {
        SpringApplication.run(Test04Mybatisplus1Application.class, args);
    }

}
