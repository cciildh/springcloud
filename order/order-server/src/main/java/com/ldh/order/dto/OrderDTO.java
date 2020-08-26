package com.ldh.order.dto;


import com.ldh.order.entity.OrderDetail;
import com.ldh.order.entity.OrderMaster;
import lombok.Data;

import java.util.List;

/**
 * order订单业务组合类
 * 一般用作业务层入参与返回值
 */
@Data
public class OrderDTO extends OrderMaster {
    private List<OrderDetail> orderDetailList;
}
