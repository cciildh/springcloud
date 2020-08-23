package com.example.product.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 实现全局异常处理
public class GlobHandler {
    //logback
    private static final Logger log = LoggerFactory.getLogger(GlobHandler.class);
}
