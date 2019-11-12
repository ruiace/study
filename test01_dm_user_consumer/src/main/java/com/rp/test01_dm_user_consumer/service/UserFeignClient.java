package com.rp.test01_dm_user_consumer.service;

import com.rp.domain.User;
import com.rp.test01_dm_user_consumer.service.fallback.UserFeignClientFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "dm-user-provider", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

    @PostMapping("/checkUser")
    public  boolean login(@RequestBody User user);
}
