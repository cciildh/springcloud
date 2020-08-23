package com.example.order.service.impl;

import com.example.order.dto.OrderDTO;
import com.example.order.entity.OrderMaster;
import com.example.order.enums.OrderPayStatusEnums;
import com.example.order.enums.OrderStatusEnums;
import com.example.order.repository.OrderDetailRepository;
import com.example.order.repository.OrderMasterRepository;
import com.example.order.service.OrderService;
import com.example.order.utlis.KeyUtli;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //1、查询商品服务 获取商品信息 （调用商品服务）
        //2、计算总价（调用商品服务）
        //3、扣除库存（调用商品服务）
        //4、订单入库
        OrderMaster orderMaster = new OrderMaster();
        //将买家信息Copy到master对象中
        orderDTO.setOrderId(KeyUtli.genUniqueKey());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(50));
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(OrderPayStatusEnums.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
