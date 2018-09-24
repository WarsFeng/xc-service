package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-18
 * \* Time: 上午10:27
 * \* Description:
 * \
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository repository;

    @Test
    public void testFindAll() {
        List<CmsPage> pageList = repository.findAll();
        System.out.println(pageList);
    }

    @Test
    public void testFindPage() {
        Page<CmsPage> pages = repository.findAll(PageRequest.of(0, 10));
        System.out.println(pages);
    }

    @Test
    public void testFindAllPage() {
        Pageable pageable = PageRequest.of(0, 10);
        CmsPage probe = new CmsPage();
        probe.setPageAlias("课程");
        ExampleMatcher matching = ExampleMatcher.matching().withMatcher("pageAlias", ExampleMatcher.GenericPropertyMatchers.startsWith());
        Example<CmsPage> example = Example.of(probe, matching);
        Page<CmsPage> pages = repository.findAll(example, pageable);
        System.out.println(pages);
    }

    @Test
    public void testInsert() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("t01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        // 参数列表
        ArrayList<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        // Insert
        repository.insert(cmsPage);
        System.out.println(cmsPage);
    }

    @Test
    public void testDelete() {
        repository.deleteById("5ba06e0f3f1ba1525a9aca3b");
    }

    @Test
    public void testUpdate() {
        Optional<CmsPage> cmsPageOptional = repository.findById("5ba06f233f1ba154cad8d59f");
        if (cmsPageOptional.isPresent()) {
            CmsPage cmsPage = cmsPageOptional.get();
            cmsPage.setPageAlias("修改过的测试页面");
            repository.save(cmsPage);
        }
    }

}
