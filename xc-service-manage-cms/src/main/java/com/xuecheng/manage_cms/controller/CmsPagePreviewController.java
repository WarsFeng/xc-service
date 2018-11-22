package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-23
 * \* Time: 上午12:36
 * \* Description:
 * \
 */
@Controller
public class CmsPagePreviewController extends BaseController {

    private final CmsPageService service;

    @Autowired
    public CmsPagePreviewController(CmsPageService service) {
        this.service = service;
    }

    @GetMapping("/cms/preview/{id}")
    public void preview(@PathVariable("id") String id) {
        String htmlContent = service.getPageHtml(id);
        try {
            response.getOutputStream().write(htmlContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
