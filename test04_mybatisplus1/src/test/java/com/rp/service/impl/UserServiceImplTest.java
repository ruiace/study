package com.rp.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rp.entity.User;
import com.rp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserServiceImplTest {


    @Autowired
    private UserService userService;


    @Test
    void test3(){
        QueryWrapper<User> mapper = new QueryWrapper();
        mapper.select("id","name").like("name","王");
        List<Object> objects = userService.listObjs(mapper);
        objects.forEach(System.out::println);
    }

    @Test
    void test2(){
        List<User> list = userService.list();
        list.forEach(System.out::println);
    }


    @Test
    void test1(){
        int count = userService.count();
        System.out.println("--------->"+count);
    }

}