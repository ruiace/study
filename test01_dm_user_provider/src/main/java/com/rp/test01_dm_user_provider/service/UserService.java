package com.rp.test01_dm_user_provider.service;

import com.rp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public  String login(@RequestParam("count") int count) throws  Exception {
//        logger.info("access @ provider 8080 as " + count);
//        return "用户已验证";
//    }
    @PostMapping("/checkUser")
    public  boolean login(@RequestBody User user){

        String name = Thread.currentThread().getName();

        logger.info("当前线程名称={}",name);

        return "admin".equals(user.getName()) && "18".equals(user.getAge());
    }

    /*public  boolean login(@RequestParam("name") String name, @RequestParam("pwd") String pwd) throws  Exception {
        return "admin".equals(name) && "12345".equals(pwd);
    }*/

}
