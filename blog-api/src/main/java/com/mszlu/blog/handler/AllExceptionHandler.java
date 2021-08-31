package com.mszlu.blog.handler;

import com.mszlu.blog.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AllExceptionHandler {

    /**
     * 通用异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
