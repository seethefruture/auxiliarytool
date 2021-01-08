package com.cxxy.edu.ErrorHandler;

import com.cxxy.edu.CustomException.InternalServerError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author Administrator
 * @title: errorHandler
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/4/2017:38
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = InternalServerError.class)
    @ResponseBody
    public String InternalServerErrorHandler(InternalServerError e) {
        System.out.println("服务器内部异常" + e.getMessage());
        return "服务器内部异常";
    }

    @ExceptionHandler(value = IOException.class)
    @ResponseBody
    public String IOExceptionErrorHandler(IOException e) {
        System.out.println("客户端关闭了连接" + e.getMessage());
        return "服务器内部异常";
    }

}
