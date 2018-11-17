package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/5.
 */
@ToString
public enum CmsCode implements ResultCode {
    CMS_ADDPAGE_EXISTSNAME(false, 24001, "页面名称已存在！"),
    CMS_GENERATE_HTML_DATA_URL_ISNULL(false, 24002, "从页面信息中找不到获取数据的url！"),
    CMS_GENERATE_HTML_DATA_ISNULL(false, 24003, "根据页面的数据url获取不到数据！"),
    CMS_GENERATE_HTML_TEMPLATE_ISNULL(false, 24004, "页面模板为空！"),
    CMS_GENERATE_HTML_HTML_ISNULL(false, 24005, "生成的静态html为空！"),
    CMS_GENERATE_HTML_SAVEHTML_ERROR(false, 24006, "保存静态html出错！"),
    CMS_COURSE_PERVIEW_ISNULL(false, 24007, "预览页面为空！"),
    CMS_PAGE_NOT_EXISTS(false, 24008, "页面不存在！"),
    CMS_SITE_NOT_EXISTS(false, 24009, "站点不存在！"),
    CMS_GENERATE_HTML_TEMPLATE_FILE_NOT_EXISTS(false, 24010, "页面模板文件不存在！"),
    CMS_PAGE_FILE_NOT_EXISTS(false, 24011, "页面文件不存在！"),
    CMS_PAGE_FILE_READ_ERROR(false, 24012, "页面读取错误！");
    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private CmsCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
