package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-9
 * \* Time: 下午8:00
 * \* Description: Exception catch class
 * \
 */
@Slf4j
@ControllerAdvice
public class ExceptionCatch {

    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    // Custom exception
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseResult customException(CustomException e) {
        log.error("Catch exception: {}\r\nException: ", e.getMessage(), e);
        return new ResponseResult(e.getResultCode());
    }

    // Other exception
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception e) {
        log.error("Catch exception: {}\r\nException: ", e.getMessage(), e);
        if (null == EXCEPTIONS)
            EXCEPTIONS = builder.build();
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());

        if (null != resultCode)
            return new ResponseResult(resultCode);
        else
            return new ResponseResult(CommonCode.SERVER_ERROR);
    }

    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }


}
