package com.rp.service.kill.config;

import com.google.common.collect.Maps;
import com.rp.service.kill.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用化 Rabbitmq 配置
 */
@Configuration
public class RabbitmqConfig {

    private final static Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Autowired
    private Environment env;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        return factory;
    }

    /**
     * 多个消费者
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //确认消费模式-NONE
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.concurrency",int.class));
        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.max-concurrency",int.class));
        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.simple.prefetch",int.class));
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.warn("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

//--------------------

    /**
          * 延时队列交换机
          *
          * @return
          */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(Constant.DELAY_EXCHANGE, "x-delayed-message", true, false,args);
    }

    /**
     * 定义延时队列
     *
     */
    @Bean
    public Queue delayQueue() {
        return new Queue(Constant.DELAY_QUEUE, true);
    }

    /**
            * 给延时队列绑定交换机
            *
            * @return
            */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(Constant.DELAY_KEY).noargs();
    }

    //--------------------
    //构建异步发送邮箱通知的消息模型
    @Bean
    public Queue successEmailQueue(){
        return new Queue(env.getProperty("mq.kill.item.success.email.queue"),true);
    }

    @Bean
    public TopicExchange successEmailExchange(){
        return new TopicExchange(env.getProperty("mq.kill.item.success.email.exchange"),true,false);
    }

    @Bean
    public Binding successEmailBinding(){
        return BindingBuilder.bind(successEmailQueue()).to(successEmailExchange()).with(env.getProperty("mq.kill.item.success.email.routing.key"));
    }


//--------------------









    //构建秒杀成功之后-订单超时未支付的死信队列消息模型

    @Bean
    public Queue successKillDeadQueue(){
        Map<String, Object> argsMap= Maps.newHashMap();
        argsMap.put("x-dead-letter-exchange","kill.dead.exchange");
        argsMap.put("x-dead-letter-routing-key","kill.dead.routing.key");
        return new Queue("kill.dead.queue",true,false,false,argsMap);
    }

    //基本交换机
    @Bean
    public TopicExchange successKillDeadProdExchange(){
        return new TopicExchange("kill.dead.prod.exchange",true,false);
    }

    //创建基本交换机+基本路由 -> 死信队列 的绑定
    @Bean
    public Binding successKillDeadProdBinding(){
        return BindingBuilder.bind(successKillDeadQueue()).to(successKillDeadProdExchange()).with("kill.prod.routing.key");
    }

    //真正的队列
    @Bean
    public Queue successKillRealQueue(){
        return new Queue("kill.dead.real.queue",true);
    }

    //死信交换机
    @Bean
    public TopicExchange successKillDeadExchange(){
        return new TopicExchange("kill.dead.exchange",true,false);
    }

    //死信交换机+死信路由->真正队列 的绑定
    @Bean
    public Binding successKillDeadBinding(){
        return BindingBuilder.bind(successKillRealQueue()).to(successKillDeadExchange()).with("kill.dead.routing.key");
    }


















//--------------------

    //构建秒杀成功之后-订单超时未支付的死信队列消息模型

    @Bean
    public Queue deadQueue(){
        Map<String, Object> argsMap= Maps.newHashMap();
        argsMap.put("x-dead-letter-exchange","realExchange");
        argsMap.put("x-dead-letter-routing-key","realkey");
        return new Queue("deadQueue",true,false,false,argsMap);
    }

    //基本交换机
    @Bean
    public TopicExchange deadExchange1(){
        return new TopicExchange("deadExchange1",true,false);
    }

    //创建基本交换机+基本路由 -> 死信队列 的绑定
    @Bean
    public Binding prodBinding(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange1()).with("deadkey");
    }

    //真正的队列
    @Bean
    public Queue realQueue(){
        return new Queue("realQueue",true);
    }

    //死信交换机
    @Bean
    public TopicExchange realExchange(){
        return new TopicExchange("realExchange",true,false);
    }

    //死信交换机+死信路由->真正队列 的绑定
    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(realQueue()).to(realExchange()).with("realkey");
    }

//--------------------

    // 创建一个立即消费队列
    @Bean
    public Queue immediateQueue() {
        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
        return new Queue("IMMEDIATE_QUEUE", true);
    }


    @Bean
    public DirectExchange immediateExchange() {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange("IMMEDIATE_EXCHANGE", true, false);
    }


    @Bean
    //把立即消费的队列和立即消费的exchange绑定在一起
    public Binding immediateBinding() {
        return BindingBuilder.bind(immediateQueue()).to(immediateExchange()).with("IMMEDIATE_ROUTING_KEY");
    }

    // 创建一个延时队列
    @Bean
    public Queue delayQueue1() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", "IMMEDIATE_EXCHANGE");
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", "IMMEDIATE_ROUTING_KEY");
        return new Queue("DELAY_QUEUE", true, false, false, params);
    }



    @Bean
    public DirectExchange deadLetterExchange() {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange("DEAD_LETTER_EXCHANGE", true, false);
    }



    @Bean
    //把立即消费的队列和立即消费的exchange绑定在一起
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue1()).to(deadLetterExchange()).with("DELAY_ROUTING_KEY");
    }




    //===================



    //构建秒杀成功之后-订单超时未支付的死信队列消息模型

    @Bean
    public Queue queue1(){
        Map<String, Object> argsMap= Maps.newHashMap();
        argsMap.put("x-dead-letter-exchange","exchange2");
        argsMap.put("x-dead-letter-routing-key","key2");
        return new Queue("queue1",true,false,false,argsMap);
    }

    //基本交换机
    @Bean
    public TopicExchange exchange1(){
        return new TopicExchange("exchange1",true,false);
    }

    //创建基本交换机+基本路由 -> 死信队列 的绑定
    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(queue1()).to(exchange1()).with("key1");
    }

    //真正的队列
    @Bean
    public Queue queue2(){
        return new Queue("queue2",true);
    }

    //死信交换机
    @Bean
    public TopicExchange exchange2(){
        return new TopicExchange("exchange2",true,false);
    }

    //死信交换机+死信路由->真正队列 的绑定
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(queue2()).to(exchange2()).with("key2");
    }


}






























































































