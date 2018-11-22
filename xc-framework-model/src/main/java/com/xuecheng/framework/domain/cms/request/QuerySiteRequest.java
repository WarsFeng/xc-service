package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-27
 * \* Time: 下午10:53
 * \* Description:
 * \
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuerySiteRequest extends RequestData {

    // 站点Id
    @ApiModelProperty("站点Id")
    private String siteId;

    // 站点名称
    @ApiModelProperty("站点名称")
    private String siteName;

    // 站点端口
    @ApiModelProperty("站点端口")
    private String sitePort;


    public boolean isEmpty() {
        return null == siteId && null == siteName && null == sitePort;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

}
