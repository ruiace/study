package com.rp.service.kill.service;

import com.rp.service.kill.dto.Booking;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
@Configuration
public class ImmediateReceiver {

    @RabbitListener(queues = "IMMEDIATE_QUEUE")
    @RabbitHandler
    public void get(Booking booking) {
        System.out.println("收到延时消息了" + booking);
    }
}
