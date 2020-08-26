package com.ldh.order.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * order参数校验
 */
@Data
public class OrderForm {
    /**
     * 买家姓名
     */
    @NotEmpty(message = "买家姓名必填")
    private String name;
    /**
     * 买家手机号
     */
    @NotEmpty(message = "买家手机号必填")
    private String phone;
    /**
     * 买家地址
     */
    @NotEmpty(message = "买家地址必填")
    private String addres;
    /**
     * 买家微信
     */
    private String openid;
    /**
     * 购买的商品信息
     */
    @NotEmpty(message = "购买商品信息不能为空")
    private String items;
}
