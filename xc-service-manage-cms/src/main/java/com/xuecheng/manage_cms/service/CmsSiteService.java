package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;

public interface CmsSiteService {

    /**
     * 分页查询站点
     *
     * @param page             页码
     * @param size             每页记录数
     * @param querySiteRequest 查询条件
     * @return 页面列表和条数
     */
    QueryResponseResult findList(int page, int size, QuerySiteRequest querySiteRequest);

}
