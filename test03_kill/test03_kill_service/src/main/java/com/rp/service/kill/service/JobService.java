package com.rp.service.kill.service;

import com.rp.service.kill.entity.ItemKillSuccess;
import com.rp.service.kill.mapper.ItemKillSuccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Autowired
    private Environment env;

    @Scheduled(cron = "0/10 * * * * ?")
    public void test(){
        List<ItemKillSuccess> itemKillSuccesses = itemKillSuccessMapper.selectOrderExpireList();
//        for (ItemKillSuccess itemKillSuccess : itemKillSuccesses) {
//            System.out.println(itemKillSuccess.toString());
//            System.out.println("定时任务=================");
//        }

        System.out.println("-------------Scheduled-------------------");
        itemKillSuccesses.forEach( v -> {
            System.out.println(v.toString()+"================");

            if(v != null && v.getDiffTime() > env.getProperty("scheduler.expire.orders.time",Integer.class)){


                System.out.println("==============================");
                System.out.println("==============================");
                System.out.println("==============================");
                System.out.println("==============================");
                System.out.println("==============================");
                System.out.println("==============================");
                System.out.println("==============================");
                System.out.println("==============================");
            }

        });

    }
}
