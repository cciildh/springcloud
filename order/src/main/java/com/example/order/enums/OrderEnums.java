package com.example.order.enums;

import lombok.Getter;

public  class OrderEnums {
    @Getter
    public enum OrderStatusEnums {
        NEW(0,"新增"),
        FINISHEED(1,"完结"),
        CANCEL(2,"取消");

        private  Integer code;
        private  String message;

        OrderStatusEnums(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
