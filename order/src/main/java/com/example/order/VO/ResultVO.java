package com.example.order.VO;

import lombok.Data;

/**
 * 统一返回结果集
 */
@Data
public class ResultVO {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 具体内容
     */
    private Object data;
}
