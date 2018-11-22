package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.xuecheng.test.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-14
 * \* Time: 下午9:44
 * \* Description:
 * \
 */
@Component
public class Consumer06_Spring {

    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_SMS})
    public void smsMessage(String messageValue, Message message, Channel channel) {
        System.out.println("Message: " + messageValue);
        System.out.println("Message: " + message.toString());
        System.out.println(channel);
    }

    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_MAIL})
    public void mailMessage(String messageValue, Message message, Channel channel) {
        System.out.println("Message: " + messageValue);
        System.out.println("Message: " + message.toString());
        System.out.println(channel);
    }
}
