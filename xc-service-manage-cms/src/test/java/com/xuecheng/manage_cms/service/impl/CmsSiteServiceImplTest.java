package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsSiteServiceImplTest {

    @Autowired
    CmsSiteServiceImpl service;

    @Test
    public void findList() {
        QueryResponseResult result = service.findList(0, 10, new QuerySiteRequest());
        Assert.assertNotNull(result);
    }
}