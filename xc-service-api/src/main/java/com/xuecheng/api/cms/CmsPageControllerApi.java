package com.xuecheng.api.cms;

import com.xuecheng.framework.model.request.RequestData;
import com.xuecheng.framework.model.response.ResponseResult;

public interface CmsPageControllerApi {

    ResponseResult pageList(int page, int size, RequestData data);

}
