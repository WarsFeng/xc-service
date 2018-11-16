package com.xuecheng.test.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-13
 * \* Time: 下午10:15
 * \* Description:
 * \
 */
@Configuration
public class RabbitConfig {

    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String QUEUE_INFORM_MAIL = "queue_inform_mail";
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    @Bean(EXCHANGE_TOPICS_INFORM)
    public TopicExchange exchangeTopicsInform() {
        return new TopicExchange(EXCHANGE_TOPICS_INFORM);
    }

    @Bean(QUEUE_INFORM_SMS)
    public Queue queueInformSms() {
        return new Queue(QUEUE_INFORM_SMS, false);
    }

    @Bean(QUEUE_INFORM_MAIL)
    public Queue queueInformMail() {
        return new Queue(QUEUE_INFORM_MAIL, false);
    }

    @Bean
    public Binding bindingQueueInformSms(@Qualifier(QUEUE_INFORM_SMS) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#");
    }

    @Bean
    public Binding bindingQueueInformMail(@Qualifier(QUEUE_INFORM_MAIL) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.mail.#");
    }


}
