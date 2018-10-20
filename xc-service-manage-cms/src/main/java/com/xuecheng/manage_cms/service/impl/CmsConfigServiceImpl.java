package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-20
 * \* Time: 下午2:04
 * \* Description:
 * \
 */
@Service
public class CmsConfigServiceImpl implements CmsConfigService {

    private final CmsConfigRepository repository;

    @Autowired
    public CmsConfigServiceImpl(CmsConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public CmsConfig getConfigById(String id) {
        Optional<CmsConfig> cmsConfig = repository.findById(id);
        return cmsConfig.orElse(null);
    }
}
