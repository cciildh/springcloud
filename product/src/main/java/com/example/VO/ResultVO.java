package com.example.VO;

import lombok.Data;

/**
 * ResultVO
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