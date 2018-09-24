package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-18
 * \* Time: 下午12:55
 * \* Description:
 * \
 */
@Service
public class CmsPageServiceImpl implements CmsPageService {

    private final CmsPageRepository repository;

    @Autowired
    public CmsPageServiceImpl(CmsPageRepository repository) {
        this.repository = repository;
    }

    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        // Page and size process
        if (0 >= page)
            page = 1;
        // Abstract page to real page
        --page;
        if (0 >= size)
            size = 20;

        // Find
        CmsPage probe = new CmsPage();
        ExampleMatcher matching = ExampleMatcher.matching();
        //  Custom conditional find
        if (null != queryPageRequest) {
            if (StringUtils.isNotEmpty(queryPageRequest.getSiteId()))
                probe.setSiteId(queryPageRequest.getSiteId());
            if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId()))
                probe.setTemplateId(queryPageRequest.getTemplateId());

            // Fuzzy find(contains)
            if (StringUtils.isNotEmpty(queryPageRequest.getPageAlias())) {
                probe.setPageAlias(queryPageRequest.getPageAlias());
                matching = matching.withMatcher("pageAlias", ExampleMatcher.GenericPropertyMatchers.contains());
            }
        }
        Example<CmsPage> example = Example.of(probe, matching);
        // Execute find
        Page<CmsPage> pages = repository.findAll(example, PageRequest.of(page, size));

        // Result
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(pages.getContent());
        queryResult.setTotal(pages.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}
