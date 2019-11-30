package com.rp.service.kill.service;

import com.rabbitmq.client.Channel;
import com.rp.service.kill.common.Constant;
import com.rp.service.kill.dto.Booking;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MqReceiver {



	@Autowired
	private Environment env;



	/**
	 * rabbitmq_delayed_message_exchange插件版延时队列
	 *delayed = true 就是说明使用rabbitmq-delayed-message-exchange
	 * @param msg
	 * @param message
	 */
	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = Constant.DELAY_QUEUE, durable = "true"),
			exchange = @Exchange(value = Constant.DELAY_EXCHANGE, type = ExchangeTypes.DIRECT,
					arguments = @Argument(name = "x-delayed-type", value = "direct"), delayed = Exchange.TRUE),
			key = Constant.DELAY_KEY))
	public void receiveDelay(String msg, Message message) {
		System.out.println(message.toString());
		System.out.println(message.getMessageProperties().getReceivedDelay());
		System.out.println("-----------收到消息:" + msg + ",当前时间：" + new Date());
	}


	/**
	 * rabbitmq_delayed_message_exchange插件版延时队列
	 *delayed = true 就是说明使用rabbitmq-delayed-message-exchange
	 * @param msg
	 * @param message
	 */
//	@RabbitListener(
//			bindings = @QueueBinding(value = @Queue(value = "test.kill.item.success.kill.dead.real.queue", durable = "true"),
//			exchange = @Exchange(value = "test.kill.item.success.kill.dead.exchange",type = ExchangeTypes.TOPIC))
//	)

	@RabbitHandler
	@RabbitListener(queues = "kill.dead.real.queue")
	public void receiveDelay1(String msg, Message message, Channel channel) {



		System.out.println(message.toString());
		//System.out.println(message.getMessageProperties().getReceivedDelay());
		System.out.println("-----------收到消息---------------:" + msg + ",当前时间：" + new Date());
	}



//	@RabbitHandler
//	@RabbitListener(queues = "realQueue-test")
//	public void receiveDelay2(String msg, Message message, Channel channel) {
//		System.out.println(message.toString());
//		System.out.println(message.getMessageProperties().getReceivedDelay());
//
//		System.out.println("-----------收到消息---------------:" + msg + ",当前时间：" + new Date());
//	}


	@RabbitHandler
	@RabbitListener(queues = "realQueue")
	public void receiveDelay4(String booking) {


		System.out.println("-----------收到消息---------------:" + booking.toString() + ",当前时间：" + new Date());
	}

}