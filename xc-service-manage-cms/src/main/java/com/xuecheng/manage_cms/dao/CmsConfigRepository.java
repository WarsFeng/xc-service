package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-20
 * \* Time: 下午2:02
 * \* Description:
 * \
 */
@Repository
public interface CmsConfigRepository extends MongoRepository<CmsConfig, String> {
}
