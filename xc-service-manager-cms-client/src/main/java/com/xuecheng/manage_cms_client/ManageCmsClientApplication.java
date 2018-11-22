package com.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-16
 * \* Time: 下午10:42
 * \* Description:
 * \
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.xuecheng.framework.domain"})
@ComponentScan(basePackages = {
        "com.xuecheng.framework",
        "com.xuecheng.manage_cms_client"
})
public class ManageCmsClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class, args);
    }
}
