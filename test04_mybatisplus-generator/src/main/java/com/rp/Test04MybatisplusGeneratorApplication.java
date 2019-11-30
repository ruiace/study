package com.rp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages={"com.rp.dao"})
public class Test04MybatisplusGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test04MybatisplusGeneratorApplication.class, args);
    }

}
