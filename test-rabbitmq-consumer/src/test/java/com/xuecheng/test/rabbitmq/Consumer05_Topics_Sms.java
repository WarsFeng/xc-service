package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-11-2
 * \* Time: 下午2:21
 * \* Description:
 * \
 */
public class Consumer05_Topics_Sms {
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_TOPIC_INFORM = "exchange_topic_inform";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(QUEUE_INFORM_SMS, false, false, false, null);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPIC_INFORM, "inform.#.sms.#");

            channel.basicConsume(QUEUE_INFORM_SMS, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println(new String(body));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
