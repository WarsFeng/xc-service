package com.xuecheng.framework.model.request;

import lombok.Data;
import lombok.ToString;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-17
 * \* Time: 下午11:09
 * \* Description:
 * \
 */
@Data
@ToString
public class RequestData {

    // 站点id
    private String siteId;
    // 页面id
    private String pageId;
    // 模板id
    private String templateId;
    // 页面名称
    private String pageName;
    // 别名
    private String pageAlias;

}
