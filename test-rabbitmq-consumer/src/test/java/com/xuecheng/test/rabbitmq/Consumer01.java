package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer01 {

    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        // Config factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        // Create connection and channel
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        // Create queue
        channel.queueDeclare(QUEUE, true, false, false, null);
        // Override get message method
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                // Exchange
                String exchange = envelope.getExchange();
                // Message id
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println("exchange:{" + exchange + "}");
                System.out.println("id:{" + deliveryTag + "}");
                System.out.println("message:{" + new String(body) + "}");
            }
        };
        channel.basicConsume(QUEUE, false, consumer);
    }

}
