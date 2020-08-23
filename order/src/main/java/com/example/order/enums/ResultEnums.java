package com.example.order.enums;

import lombok.Getter;

@Getter
public enum ResultEnums {
    PARAM_ERROR(4001, "参数错误"),
    RUN_ERROR(4002, "运行错误"),
    SYSTEM_ERROR(5000, "系统错误"),
    ;

    private Integer code;
    private String message;

    ResultEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
