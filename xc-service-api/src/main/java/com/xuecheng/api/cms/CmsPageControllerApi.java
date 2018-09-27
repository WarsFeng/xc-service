package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "Cms页面管理接口", description = "Cms页面管理接口, 提供页面的增、删、改、查")
public interface CmsPageControllerApi {

    /**
     * 页面列表分页查询
     *
     * @param page             页码
     * @param size             页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表和条数
     */
    @ApiOperation("分页查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 添加页面
     *
     * @param cmsPage 页面实体
     * @return 含有带Id的cmsPage
     */
    @ApiOperation("添加页面")
    CmsPageResult add(CmsPage cmsPage);

}
