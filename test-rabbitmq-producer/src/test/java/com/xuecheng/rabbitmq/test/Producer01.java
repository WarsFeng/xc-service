package com.xuecheng.rabbitmq.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer01 {

    private static final String QUEUE = "helloworld";

    public static void main(String[] args) {
        // Create factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        try (
                // Create connection
                Connection connection = connectionFactory.newConnection();
                // Create channel
                Channel channel = connection.createChannel()
        ) {
            String message = "HelloWorld小明" + System.currentTimeMillis();
            // Declare queue
            channel.queueDeclare(QUEUE, true, false, false, null);
            // Publish message
            channel.basicPublish("", QUEUE, null, message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Close
    }

}
