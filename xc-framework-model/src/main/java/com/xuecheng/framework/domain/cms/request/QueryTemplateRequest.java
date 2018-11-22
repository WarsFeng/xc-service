package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-29
 * \* Time: 下午9:34
 * \* Description:
 * \
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryTemplateRequest extends RequestData {

    @ApiModelProperty("模板Id")
    public String templateId;

    @ApiModelProperty("站点Id")
    public String siteId;

    @ApiModelProperty("模板名称")
    public String templateName;

    public boolean isEmpty() {
        return null == templateId && null == siteId && null == templateName;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

}
