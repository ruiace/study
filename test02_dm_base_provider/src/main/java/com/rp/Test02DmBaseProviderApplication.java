package com.rp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Test02DmBaseProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(Test02DmBaseProviderApplication.class, args);
	}

}
