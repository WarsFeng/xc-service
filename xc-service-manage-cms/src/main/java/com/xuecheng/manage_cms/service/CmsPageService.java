package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;

public interface CmsPageService {

    /**
     * 页面列表分页查询
     * @param page 页码
     * @param size 页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表和条数
     */
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 添加页面
     *
     * @param cmsPage 页面实体
     * @return 含有带Id的cmsPage
     */
    CmsPageResult add(CmsPage cmsPage);
}
