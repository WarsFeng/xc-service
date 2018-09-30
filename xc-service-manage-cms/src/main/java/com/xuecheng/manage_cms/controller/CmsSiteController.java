package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsSiteControllerApi;
import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsSiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-27
 * \* Time: 下午11:14
 * \* Description:
 * \
 */

@Slf4j
@RestController
@RequestMapping("/cms/site")
public class CmsSiteController implements CmsSiteControllerApi {

    private final CmsSiteService service;

    @Autowired
    public CmsSiteController(CmsSiteService service) {
        this.service = service;
    }

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QuerySiteRequest querySiteRequest) {
        log.info("Query site page: {},size: {},params: {}", page, size, querySiteRequest);
        return service.findList(page, size, querySiteRequest);
    }

}
