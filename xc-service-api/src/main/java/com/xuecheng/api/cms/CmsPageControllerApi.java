package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
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

    /**
     * 根据Id查询页面
     *
     * @param id 页面Id
     * @return 页面
     */
    @ApiOperation("根据Id查询页面")
    @ApiImplicitParam(name = "id", value = "Page id", required = true, paramType = "path", dataType = "String")
    CmsPage findById(String id);

    /**
     * 修改页面信息
     *
     * @param id      要修改页面的Id
     * @param cmsPage 要修改的页面信息
     * @return 修改后的CmsPage实体
     */
    @ApiOperation("修改页面")
    @ApiImplicitParam(name = "id", value = "Page id", required = true, paramType = "path", dataType = "String")
    CmsPageResult edit(String id, CmsPage cmsPage);

    /**
     * 删除页面
     *
     * @param id 要删除页面的Id
     * @return 是否成功
     */
    @ApiOperation("删除页面")
    @ApiImplicitParam(name = "id", value = "Page id", required = true, paramType = "path", dataType = "String")
    ResponseResult delete(String id);

    /**
     * 发布页面, 保存静态化页面并向MQ发送Message
     *
     * @param id 发布页面Id
     * @return 是否成功
     */
    @ApiOperation("发布页面")
    @ApiImplicitParam(name = "id", value = "Page id", required = true, paramType = "path", dataType = "String")
    ResponseResult post(String id);

}
