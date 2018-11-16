package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-30
 * \* Time: 下午10:08
 * \* Description: Routing method the sms consumer
 * \
 */
public class Consumer04_Routing_Mail {

    private final static String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    private final static String QUEUE_INFORM_MAIL = "queue_inform_mail";

    public static void main(String[] args) {
        // Configure factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        try {
            // Create connection and channel
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // Declare queue and exchange
            channel.queueDeclare(QUEUE_INFORM_MAIL, true, false, false, null);
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            // Bind queue to exchange
            channel.queueBind(QUEUE_INFORM_MAIL, EXCHANGE_ROUTING_INFORM, "mail");

            channel.basicConsume(QUEUE_INFORM_MAIL, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("NEW Message id: " + envelope.getDeliveryTag() + ", routingKey: " + envelope.getRoutingKey() + ", exchange: " + envelope.getExchange() + ", body:{" + new String(body) + "}");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
