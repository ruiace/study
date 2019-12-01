package com.rp.service.kill.service.impl;

import com.rp.service.kill.dto.KillSuccessUserInfo;
import com.rp.service.kill.mapper.ItemKillSuccessMapper;
import com.rp.service.kill.service.RabbitMqSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqSendServiceImpl implements RabbitMqSendService {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Override
    public void killSuccessSendEmail(String orderNo) {
        log.info("发送消息 rabbitmq ={}", orderNo);

        KillSuccessUserInfo info = itemKillSuccessMapper.selectByOrderNo(orderNo);
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.kill.item.success.email.exchange"));
            rabbitTemplate.setRoutingKey(env.getProperty("mq.kill.item.success.email.routing.key"));

//            Message build = MessageBuilder.withBody(orderNo.getBytes("UTF-8")).build();
//            rabbitTemplate.convertAndSend(build);
            rabbitTemplate.convertAndSend(info, message -> {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, KillSuccessUserInfo.class);
                        return message;
                    }
            );

        } catch (Exception e) {
            log.error("发送消息异常");
            e.printStackTrace();
        }
    }


    @Override
    public void killSuccessSendExpireOrder(String orderNo) {
        log.info("发送过期订单消息 rabbitmq ={}", orderNo);

        KillSuccessUserInfo info = itemKillSuccessMapper.selectByOrderNo(orderNo);
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange("exchange1");
            rabbitTemplate.setRoutingKey("key1");

//            Message build = MessageBuilder.withBody(orderNo.getBytes("UTF-8")).build();
//            rabbitTemplate.convertAndSend(build);
            rabbitTemplate.convertAndSend(info, message -> {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, KillSuccessUserInfo.class);
                        messageProperties.setExpiration("30000");
                        return message;
                    }
            );

        } catch (Exception e) {
            log.error("发送过期订单消息异常");
            e.printStackTrace();
        }
    }
}
