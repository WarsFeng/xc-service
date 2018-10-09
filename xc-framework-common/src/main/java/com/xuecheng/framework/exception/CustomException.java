package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-9
 * \* Time: 下午7:52
 * \* Description:
 * \
 */

public class CustomException extends RuntimeException {

    private ResultCode resultCode;

    CustomException(ResultCode resultCode) {
        super("错误代码: " + resultCode.code() + " 错误信息: " + resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
