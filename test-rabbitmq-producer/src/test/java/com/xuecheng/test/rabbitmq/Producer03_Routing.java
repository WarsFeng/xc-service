package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-30
 * \* Time: 下午9:55
 * \* Description: Routing method
 * \
 */
public class Producer03_Routing {

    private final static String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    private final static String QUEUE_INFORM_EMAIL = "queue_inform_mail";
    private final static String QUEUE_INFORM_SMS = "queue_inform_sms";

    public static void main(String[] args) {
        // Configure factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        // Create connection and channel
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // Declare exchange and queue
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            // Bind queue to exchange
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, "mail");
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, "sms");
            // Publish
            String smsMessage = "SMS Message Time:{" + new Date() + "}";
            String mailMessage = "MAIL Message Time:{" + new Date() + "}";
            channel.basicPublish(EXCHANGE_ROUTING_INFORM, "sms", null, smsMessage.getBytes());
            channel.basicPublish(EXCHANGE_ROUTING_INFORM, "mail", null, mailMessage.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
