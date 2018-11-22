package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-22
 * \* Time: 下午4:40
 * \* Description:
 * \
 */
@Mapper
@Repository
public interface CourseMapper {
    CourseBase findCourseBaseById(String id);
}
