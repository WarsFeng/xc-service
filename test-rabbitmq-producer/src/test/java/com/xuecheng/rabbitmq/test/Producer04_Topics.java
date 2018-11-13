package com.xuecheng.rabbitmq.test;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-11-1
 * \* Time: 上午11:13
 * \* Description:
 * \
 */
public class Producer04_Topics {

    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String QUEUE_INFORM_MAIL = "queue_inform_mail";
    private static final String EXCHANGE_TOPIC_INFORM = "exchange_topic_inform";

    public static void main(String[] args) {
        // Configure factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        // Create connection and channel
        try (
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            // Declare queue and exchange
            channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(QUEUE_INFORM_SMS, false, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_MAIL, false, false, false, null);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPIC_INFORM, "inform.#.sms.#");
            channel.queueBind(QUEUE_INFORM_MAIL, EXCHANGE_TOPIC_INFORM, "inform.#.mail.#");
            // Send Message
            channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.mail", null, "mail".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
