package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-30
 * \* Time: 下午1:32
 * \* Description:
 * \
 */

@Service
public class CmsTemplateServiceImpl implements CmsTemplateService {

    private final CmsTemplateRepository repository;

    @Autowired
    public CmsTemplateServiceImpl(CmsTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest) {
        // Page and size process
        if (0 >= page)
            page = 1;
        // Abstract page to real page
        page--;
        if (0 >= size)
            size = 10;
        Pageable pageable = PageRequest.of(page, size);

        // Custom conditional find
        Page<CmsTemplate> pages;
        if (queryTemplateRequest.isNotEmpty()) {
            Example<CmsTemplate> example;
            CmsTemplate probe = new CmsTemplate();
            if (isNotEmpty(queryTemplateRequest.siteId))
                probe.setSiteId(queryTemplateRequest.getSiteId());
            if (isNotEmpty(queryTemplateRequest.templateId))
                probe.setTemplateId(queryTemplateRequest.getTemplateId());

            // Fuzzy find(contain)
            if (isNotEmpty(queryTemplateRequest.getTemplateName())) {
                probe.setTemplateName(queryTemplateRequest.getTemplateName());
                ExampleMatcher matching = ExampleMatcher.matching().withMatcher(
                        "templateName", ExampleMatcher.GenericPropertyMatchers.contains());
                example = Example.of(probe, matching);
            } else {
                example = Example.of(probe);
            }
            // Execute find
            pages = repository.findAll(example, pageable);
        } else {
            pages = repository.findAll(pageable);
        }

        // Result
        QueryResult<CmsTemplate> result = new QueryResult<>();
        result.setTotal(pages.getTotalElements());
        result.setList(pages.getContent());
        return new QueryResponseResult(CommonCode.SUCCESS, result);
    }

}
