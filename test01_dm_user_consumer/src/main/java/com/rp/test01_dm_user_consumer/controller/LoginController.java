package com.rp.test01_dm_user_consumer.controller;

import com.rp.domain.User;
import com.rp.test01_dm_user_consumer.service.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserFeignClient userFeignClient;

    //@RequestMapping("/userlogin")
    /*public String login() {
        return "收到结果：" + userFeignClient.login();
    }*/


    @GetMapping("/check")
    public String login() {
        //if (userFeignClient.login(name, pwd))
        /*User user = new User();
        user.setUsername(name);
        user.setPassword(pwd);
        if (userFeignClient.login(user))
            return "hello, " + name;
        else
            return "fail, " + name;*/
        //return "收到：" + userFeignClient.login();
        for (int i = 15; i < 20; ++i) {
            User user1 = new User();
            user1.setAge(i+"");
            user1.setName("admin");
            boolean login = userFeignClient.login(user1);
            System.out.println(login+"---------------------------"+i);
        }
        return "负载均衡demo";
    }
}
