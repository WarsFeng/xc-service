package com.xuecheng.test.rabbitmq;


import com.xuecheng.test.rabbitmq.config.RabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-14
 * \* Time: 下午1:20
 * \* Description:
 * \
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_Spring {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessage() {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_TOPICS_INFORM, "inform.sms.mail", "SMS and Email message!");
        System.out.println("Send message end.");
    }
}
