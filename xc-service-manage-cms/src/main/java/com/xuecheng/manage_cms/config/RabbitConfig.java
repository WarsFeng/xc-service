package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-19
 * \* Time: 下午2:22
 * \* Description: RabbitMQ config
 * \
 */
@Configuration
public class RabbitConfig {

    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";

    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EX_ROUTING_CMS_POSTPAGE() {
        return new DirectExchange(EX_ROUTING_CMS_POSTPAGE);
    }

}
