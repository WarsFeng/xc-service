package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

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
        if (queryPageRequest.isNotEmpty()) {
            if (isNotEmpty(queryPageRequest.getSiteId()))
                probe.setSiteId(queryPageRequest.getSiteId());
            if (isNotEmpty(queryPageRequest.getTemplateId()))
                probe.setTemplateId(queryPageRequest.getTemplateId());

            // Fuzzy find(contains)
            if (isNotEmpty(queryPageRequest.getPageAlias())) {
                probe.setPageAlias(queryPageRequest.getPageAlias());
                matching = matching.withMatcher("pageAlias", ExampleMatcher.GenericPropertyMatchers.contains());
            }
        }
        Example<CmsPage> example = Example.of(probe, matching);
        // Execute find
        Page<CmsPage> pages = repository.findAll(example, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "pageCreateTime")));

        // Result
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(pages.getContent());
        queryResult.setTotal(pages.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        // Check if it already exists
        CmsPage index = repository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        // Not exist
        if (null == index) {
            cmsPage.setPageId(null);    //  SpringDataJpa auto generate
            // Insert and return
            return new CmsPageResult(CommonCode.SUCCESS, repository.insert(cmsPage));
        }
        // Exist
        return new CmsPageResult(CommonCode.FAIL, null);
    }
}
