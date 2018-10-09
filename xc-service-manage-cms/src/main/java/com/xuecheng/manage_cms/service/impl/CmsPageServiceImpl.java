package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
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

import static org.apache.commons.lang3.StringUtils.isEmpty;
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
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "pageCreateTime"));

        //  Custom conditional find
        Page<CmsPage> pages;
        if (queryPageRequest.isNotEmpty()) {
            Example<CmsPage> example;
            CmsPage probe = new CmsPage();
            if (isNotEmpty(queryPageRequest.getSiteId()))
                probe.setSiteId(queryPageRequest.getSiteId());
            if (isNotEmpty(queryPageRequest.getTemplateId()))
                probe.setTemplateId(queryPageRequest.getTemplateId());

            // Fuzzy find(contains)
            if (isNotEmpty(queryPageRequest.getPageAlias())) {
                ExampleMatcher matching = ExampleMatcher.matching();
                probe.setPageAlias(queryPageRequest.getPageAlias());
                matching = matching.withMatcher("pageAlias", ExampleMatcher.GenericPropertyMatchers.contains());

                example = Example.of(probe, matching);
            } else
                example = Example.of(probe);
            // Execute query
            pages = repository.findAll(example, pageable);
        } else
            pages = repository.findAll(pageable);

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
        // Exist
        if (null != index)
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);

        // Insert and return
        cmsPage.setPageId(null);    //  SpringDataJpa auto generate
        return new CmsPageResult(CommonCode.SUCCESS, repository.insert(cmsPage));
    }

    @Override
    public CmsPage findById(String id) {
        if (isEmpty(id))
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);

        Optional<CmsPage> cmsPage = repository.findById(id);
        return cmsPage.orElse(null);
    }

    @Override
    public CmsPageResult edit(String id, CmsPage cmsPage) {
        CmsPage page = this.findById(id);

        // 更新站点名称
        page.setPageName(cmsPage.getPageName());
        // 更新站点Id
        page.setSiteId(cmsPage.getSiteId());
        // 更新模板Id
        page.setTemplateId(cmsPage.getTemplateId());
        // 更新Web访问路径
        page.setPageWebPath(cmsPage.getPageWebPath());
        // 更新实际文件路径
        page.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
        // 更新页面类型
        page.setPageType(cmsPage.getPageType());
        // 更新页面别名
        page.setPageAlias(cmsPage.getPageAlias());
        // 更新页面创建时间
        page.setPageCreateTime(cmsPage.getPageCreateTime());

        return new CmsPageResult(CommonCode.SUCCESS, repository.save(page));
    }

    @Override
    public ResponseResult delete(String id) {
        CmsPage cmsPage = this.findById(id);
        if (null == cmsPage)
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);

        repository.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
