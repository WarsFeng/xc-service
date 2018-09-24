package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryPageRequest extends RequestData {
    //站点id
    @ApiModelProperty("站点Id")
    private String siteId;

    //页面ID
    @ApiModelProperty("页面Id")
    private String pageId;

    //页面名称
    @ApiModelProperty("页面名称")
    private String pageName;

    //别名
    @ApiModelProperty("页面别名")
    private String pageAlias;

    //模版id
    @ApiModelProperty("模板Id")
    private String templateId;

}
