package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

public interface CmsPageService {

    /**
     * 页面列表分页查询
     *
     * @param page             页码
     * @param size             页面显示个数
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

    /**
     * 获取单个页面
     *
     * @param id 页面Id
     * @return 页面实体
     */
    CmsPage findById(String id);


    /**
     * 修改页面
     *
     * @param id      要修改页面的Id
     * @param cmsPage 要修改的
     * @return 修改后的CmsPage实体
     */
    CmsPageResult edit(String id, CmsPage cmsPage);

    /**
     * 静态化生成页面Html
     *
     * @param id 页面Id
     * @return Html content
     */
    String getPageHtml(String id);

    /**
     * 删除页面
     *
     * @param id 要删除页面的Id
     * @return 是否成功
     */
    ResponseResult delete(String id);
}
