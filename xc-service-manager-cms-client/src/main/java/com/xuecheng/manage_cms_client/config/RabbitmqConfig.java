package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-16
 * \* Time: 下午10:46
 * \* Description:
 * \
 */
@Configuration
public class RabbitmqConfig {

    // Queue bean  name
    private static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    // Exchange bean name
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";
    // Queue name
    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;
    // Routing key
    @Value("${xuecheng.mq.routingkey}")
    public String routingkey;

    /**
     * Create routing method exchange
     *
     * @return Exchange
     */
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EX_ROUTING_CMS_POSTPAGE() {
        return new DirectExchange(EX_ROUTING_CMS_POSTPAGE);
    }

    /**
     * Create queue
     *
     * @return Queue
     */
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_CMS_POSTPAGE() {
        return new Queue(queue_cms_postpage_name);
    }

    /**
     * Bind queue to exchange
     *
     * @param queue    queue
     * @param exchange exchange
     * @return Binding
     */
    @Bean
    public Binding BINDING_QUEUE_POSTPAGE(
            @Qualifier(QUEUE_CMS_POSTPAGE) Queue queue,
            @Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey).noargs();
    }

}
