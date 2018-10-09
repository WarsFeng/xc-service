package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-18
 * \* Time: 上午9:11
 * \* Description:
 * \
 */

@Slf4j
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    private final CmsPageService service;

    @Autowired
    public CmsPageController(CmsPageService service) {
        this.service = service;
    }


    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        log.info("Query page: {} size: {} params: {}", page, size, queryPageRequest);
        return service.findList(page, size, queryPageRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        log.info("Insert page: {}", cmsPage);
        return service.add(cmsPage);
    }

    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        log.info("Query one page, Id: {}", id);
        return service.findById(id);
    }

    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        log.info("Edit page, Id: {}, params: {}", id, cmsPage);
        return service.edit(id, cmsPage);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        log.info("Delete page, Id: {}", id);
        return service.delete(id);
    }

}
