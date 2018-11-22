package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageServiceImplTest {

    @Autowired
    CmsPageServiceImpl service;

    @Test
    public void findList() {
        QueryResponseResult list = service.findList(0, 10, new QueryPageRequest());
        Assert.assertNotNull(list);
    }

    @Test
    public void add() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setTemplateId("5abf2a8f5b05aa2ebcfce6b5");
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageAlias("测试别名");
        cmsPage.setPageWebPath("/testAdd");
        cmsPage.setPagePhysicalPath("/var/logs/");
        Assert.assertNotNull(service.add(cmsPage));
    }

    @Test
    public void findById() {
        CmsPage cmsPage = service.findById("5a795ac7dd573c04508f3a56");
        Assert.assertNotNull(cmsPage);
    }

    @Test
    public void edit() {
        CmsPage cmsPage = service.findById("5a795ac7dd573c04508f3a56");
        CmsPageResult result = service.edit("5a795ac7dd573c04508f3a56", cmsPage);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void delete() {
    }
}