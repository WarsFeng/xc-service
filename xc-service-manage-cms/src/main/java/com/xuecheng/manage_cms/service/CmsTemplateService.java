package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;

public interface CmsTemplateService {

    /**
     * 分页查询模板
     *
     * @param page                 页码
     * @param size                 每页记录数
     * @param queryTemplateRequest 查询条件
     * @return 页面列表和条数
     */
    QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest);

}
