package com.example.order.enums;

import lombok.Getter;

@Getter
public enum OrderPayStatusEnums {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;
    private Integer code;
    private String message;

    OrderPayStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
