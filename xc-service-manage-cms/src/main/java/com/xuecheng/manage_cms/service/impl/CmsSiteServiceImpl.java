package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-28
 * \* Time: 下午1:20
 * \* Description:
 * \
 */

@Service
public class CmsSiteServiceImpl implements CmsSiteService {

    private final CmsSiteRepository repository;

    @Autowired
    public CmsSiteServiceImpl(CmsSiteRepository repository) {
        this.repository = repository;
    }

    @Override
    public QueryResponseResult findList(int page, int size, QuerySiteRequest querySiteRequest) {
        // Page and size process
        if (0 >= page)
            page = 1;
        // Abstract page to real page
        --page;
        if (0 >= size)
            size = 10;
        Pageable pageAble = PageRequest.of(page, size);
        Page<CmsSite> pages;

        // Custom conditional find
        if (querySiteRequest.isNotEmpty()) {
            Example<CmsSite> example;

            // Probe
            CmsSite probe = new CmsSite();
            if (isNotEmpty(querySiteRequest.getSiteId()))
                probe.setSiteId(querySiteRequest.getSiteId());
            if (isNotEmpty(querySiteRequest.getSitePort()))
                probe.setSitePort(querySiteRequest.getSitePort());

            // Fuzzy find(contains)
            if (isNotEmpty(querySiteRequest.getSiteName())) {
                probe.setSiteName(querySiteRequest.getSiteName());
                ExampleMatcher matching = ExampleMatcher.matching()
                        .withMatcher("siteName", ExampleMatcher.GenericPropertyMatchers.contains());
                example = Example.of(probe, matching);
            } else {
                example = Example.of(probe);
            }
            // Execute find
            pages = repository.findAll(example, pageAble);
        } else {
            pages = repository.findAll(pageAble);
        }

        // Result
        QueryResult<CmsSite> queryResult = new QueryResult<>();
        queryResult.setTotal(pages.getTotalElements());
        queryResult.setList(pages.getContent());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

}
