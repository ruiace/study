package com.rp.test01_dm_user_consumer.service.fallback;

import com.rp.domain.User;
import com.rp.test01_dm_user_consumer.service.UserFeignClient;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallback implements UserFeignClient {


    @Override
    public boolean login(User user) {
        System.out.println("==============UserFeignClientFallback===================");
        System.out.println("进入fallback方法");
        return true;
    }
}
