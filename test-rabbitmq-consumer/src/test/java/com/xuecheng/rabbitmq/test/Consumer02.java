package com.xuecheng.rabbitmq.test;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-29
 * \* Time: 下午10:18
 * \* Description: Publish subscribe consumer
 * \
 */
public class Consumer02 {

    private static final String QUEUE_FANOUT_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

    public static void main(String[] args) {
        // Config and init factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        // Create connection and channel
        try {
            // Declare queue
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // Declare queue and exchange
            channel.queueDeclare(QUEUE_FANOUT_SMS, true, false, false, null);
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            // Bind queue
            channel.queueBind(QUEUE_FANOUT_SMS, EXCHANGE_FANOUT_INFORM, "");
            //
            channel.basicConsume(QUEUE_FANOUT_SMS, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)  {
                    System.out.println("Message ID:{" + envelope.getDeliveryTag() + "} Exchange:{" + envelope.getExchange() + "} Message{" + new String(body) + "}");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
