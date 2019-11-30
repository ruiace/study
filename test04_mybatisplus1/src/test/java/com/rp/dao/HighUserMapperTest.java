package com.rp.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rp.entity.HighUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.Version;

import java.util.List;

@SpringBootTest
class HighUserMapperTest {


    @Autowired
    private HighUserMapper highUserMapper;

    //1200022302918447106


    @Test
    void test5(){
        HighUser entity = new HighUser();
        entity.setId(1200022302918447106L);
        entity.setAge(30);
        entity.setEmail("3412313afd");
        entity.setName("rruipeng");
        entity.setVersion(1);
        int insert = highUserMapper.updateById(entity);

        System.out.println("---" + insert);

    }

    @Test
    void test4(){
        HighUser entity = new HighUser();
        entity.setAge(30);
        entity.setEmail("3412313afd");
        entity.setName("rruipeng");
        int insert = highUserMapper.insert(entity);

        System.out.println("---" + insert);

    }

    @Test
    void test3(){
        List<HighUser> highUsers = highUserMapper.selectMyList(Wrappers.<HighUser>lambdaQuery().gt(HighUser::getAge,30));
        highUsers.forEach(System.out::println);
        System.out.println("---");

    }


    @Test
    void test2(){
        int i = highUserMapper.deleteById(1094592041087729666L);
        System.out.println("=======>"+i);


    }

    @Test
    void test1(){
        List<HighUser> highUsers = highUserMapper.selectList(null);
        highUsers.forEach(System.out::println);
        System.out.println("---");

    }
}