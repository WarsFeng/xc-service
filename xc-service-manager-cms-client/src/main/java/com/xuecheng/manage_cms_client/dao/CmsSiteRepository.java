package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-17
 * \* Time: 下午2:48
 * \* Description:
 * \
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
