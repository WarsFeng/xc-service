package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-22
 * \* Time: 下午4:38
 * \* Description:
 * \
 */
@Repository
public interface CourseBaseRepository extends JpaRepository<CourseBase, String> {
}
