package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;

public interface CmsPageControllerApi {

    /**
     * 页面列表分页查询
     * @param page 页码
     * @param size 页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表和条数
     */
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

}
