package com.rp.service.kill.controller;

import com.rp.service.kill.common.Constant;
import com.rp.service.kill.dto.Booking;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class TestContoller {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    @GetMapping(value = "/send/{exp}")
    public String test(@PathVariable("exp") Integer exp) {
        String msg = "发送时间：" + new Date();
        rabbitTemplate.convertAndSend(Constant.DELAY_EXCHANGE, "delay.key", msg, message -> {
            message.getMessageProperties().setDelay(exp);// 单位 毫秒
            return message;
        });
        return "---------sendTime:" + new Date();
    }


    @GetMapping(value = "/sendtest/{exp}")
    public String test1(@PathVariable("exp") Integer exp) {
        String msg = "发送时间：" + new Date();
        rabbitTemplate.convertAndSend(
                "kill.dead.prod.exchange",
                "kill.prod.routing.key",
                msg,
                message -> {
                    message.getMessageProperties().setExpiration(exp +"");// 单位 毫秒
                    return message;
                });
        return "---------sendTime:" + new Date();
    }



    @GetMapping(value = "/sendtest2/{exp}")
    public String test2(@PathVariable("exp") Integer exp) {

        Booking booking = new Booking();
        booking.setBookingContent("hhaha");
        booking.setBookingName("预定房子");
        booking.setBookingTime(new Date());
        booking.setOperatorName("hellen");
        String msg = "发送时间：" + new Date();
        rabbitTemplate.convertAndSend(
                "deadExchange1",
                "deadkey",
                booking.toString(),
                message ->{
                    message.getMessageProperties().setExpiration(exp + "");
                    System.out.println(" Delay sent2." + exp);
                    return message;
                }

);
        return "---------sendTime2:" + new Date();
    }




    @GetMapping(value = "/sendtest3/")
    public String test3() {

        Booking booking = new Booking();
        booking.setBookingContent("hhaha");
        booking.setBookingName("预定房子");
        booking.setBookingTime(new Date());
        booking.setOperatorName("hellen");
        send(booking, 60000);


        return "---------sendTime3:" + new Date();
    }





    public void send(Booking booking, int delayTime) {
        System.out.println("delayTime" + delayTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.rabbitTemplate.convertAndSend("DEAD_LETTER_EXCHANGE", "DELAY_ROUTING_KEY", booking, message -> {
            message.getMessageProperties().setExpiration(delayTime + "");
            System.out.println(sdf.format(new Date()) + " Delay sent.");
            return message;
        });
    }

}