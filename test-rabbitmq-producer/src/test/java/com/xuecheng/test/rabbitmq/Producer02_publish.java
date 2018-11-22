package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-29
 * \* Time: 下午9:48
 * \* Description:
 * \
 */
public class Producer02_publish {

    private static final String QUEUE_INFORM_MAIL = "queue_inform_mail";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

    // Publish subscribe
    public static void main(String[] args) {
        // Config factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        // Open connection and channel
        try (
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            // Declare queue and exchange
            channel.queueDeclare(QUEUE_INFORM_MAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            // Bind queue to exchange
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_FANOUT_INFORM, "");
            channel.queueBind(QUEUE_INFORM_MAIL, EXCHANGE_FANOUT_INFORM, "");

            // Publish message
            String message = "Publish subscribe" + new Date();
            channel.basicPublish(EXCHANGE_FANOUT_INFORM, "", null, message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
