package com.xuecheng.manage_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-22
 * \* Time: 下午4:41
 * \* Description:
 * \
 */
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.course")
@ComponentScans({
        @ComponentScan(basePackages = "com.xuecheng.api"),
        @ComponentScan(basePackages = "com.xuecheng.manage_course"),
        @ComponentScan(basePackages = "com.xuecheng.framework")})
public class ManageCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageCourseApplication.class, args);
    }
}
