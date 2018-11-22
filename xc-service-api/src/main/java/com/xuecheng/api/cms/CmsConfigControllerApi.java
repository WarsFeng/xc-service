package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-20
 * \* Time: 下午1:56
 * \* Description:
 * \
 */
@Api(value = "Cms配置管理接口", description = "Cms配置管理接口, 提供数据模型的查询, 管理")
public interface CmsConfigControllerApi {

    @ApiOperation("根据Id查询Cms配置信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "Config id", required = true, paramType = "path", dataType = "String")
    )
    CmsConfig getModel(String id);
}
