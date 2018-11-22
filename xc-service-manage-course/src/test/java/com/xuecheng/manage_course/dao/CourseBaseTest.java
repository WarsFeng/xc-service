package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-22
 * \* Time: 下午9:57
 * \* Description:
 * \
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseBaseTest {


    @Autowired
    private CourseMapper mapper;
    @Autowired
    private CourseBaseRepository repository;
    private String id = "402885816243d2dd016243f24c030002";


    @Test
    public void testJpa() {
        Optional<CourseBase> courseBase = repository.findById(id);
        Assert.assertTrue(courseBase.isPresent());
    }

    @Test
    public void testMapper() {
        CourseBase courseBase = mapper.findCourseBaseById(id);
        Assert.assertNotNull(courseBase);
    }
}
