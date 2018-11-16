package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-29
 * \* Time: 下午10:18
 * \* Description: Publish subscribe consumer
 * \
 */
public class Consumer03 {

    private static final String QUEUE_FANOUT_MAIL = "queue_inform_mail";
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
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // Declare queue and exchange
            channel.queueDeclare(QUEUE_FANOUT_MAIL, true, false, false, null);
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            // Bind queue
            channel.queueBind(QUEUE_FANOUT_MAIL, EXCHANGE_FANOUT_INFORM, "");

            channel.basicConsume(QUEUE_FANOUT_MAIL, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("Message ID:{" + envelope.getDeliveryTag() + "} Exchange:{" + envelope.getExchange() + "} Message{" + new String(body) + "}");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
