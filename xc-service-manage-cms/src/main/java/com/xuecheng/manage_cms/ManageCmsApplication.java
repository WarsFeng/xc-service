package com.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-18
 * \* Time: 上午12:37
 * \* Description:
 * \
 */
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")    // 扫描实体
@ComponentScan(basePackages = {"com.xuecheng.api"}) //扫描接口
@ComponentScan(basePackages = {"com.xuecheng.manage_cms"})  // 扫描本项目下所有类
public class ManageCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}
