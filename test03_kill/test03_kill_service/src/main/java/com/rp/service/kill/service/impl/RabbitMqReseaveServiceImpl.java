package com.rp.service.kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.gson.JsonObject;
import com.rp.service.kill.dto.KillSuccessUserInfo;
import com.rp.service.kill.dto.MailDto;
import com.rp.service.kill.entity.ItemKillSuccess;
import com.rp.service.kill.mapper.ItemKillSuccessMapper;
import com.rp.service.kill.service.EmailService;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.ENTITYDatatypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqReseaveServiceImpl  {

    @Autowired
    private Environment env;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${mq.kill.item.success.email.queue}",containerFactory = "singleListenerContainer")
    //@RabbitHandler
    void killSuccessReseaveInfo(KillSuccessUserInfo info){

        MailDto dto = new MailDto(env.getProperty("mail.kill.item.success.subject"),"这是一个简单的邮件",new String[]{info.getEmail()});
        emailService.sendSimpleEmail(dto);
        System.out.println("===================1");
        emailService.sendSimpleEmail(dto);
        System.out.println("===================2");

        String str =String.format(env.getProperty("mail.kill.item.success.content"),info.getItemName(),info.getCode());
        MailDto dto1 = new MailDto(env.getProperty("mail.kill.item.success.subject"),str,new String[]{info.getEmail()});

        emailService.sendHtmlEmail(dto1);
        System.out.println("===================3");
        log.info("接受消息=============================={}", info.toString());

    }


    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @RabbitListener(queues = "queue2",containerFactory = "singleListenerContainer")
    void killSuccessReseaveExpireOrder(KillSuccessUserInfo info){

        ItemKillSuccess itemKillSuccess = itemKillSuccessMapper.selectById(info.getCode());
        if(itemKillSuccess != null && itemKillSuccess.getStatus() == 0){
            ItemKillSuccess entity = new ItemKillSuccess();
            entity.setStatus(-1);


            UpdateWrapper<ItemKillSuccess> lambda = new UpdateWrapper<>();
            lambda.eq("status",0).eq("code",info.getCode());

            int update = itemKillSuccessMapper.update(entity, lambda);
            log.info("killSuccessReseaveExpireOrder======update ={}",update);
        }

    }
}
