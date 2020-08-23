package com.example.order.repository;


import com.example.order.OrderApplicationTests;
import com.example.order.entity.OrderMaster;
import com.example.order.enums.OrderPayStatusEnums;
import com.example.order.enums.OrderStatusEnums;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testSave() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress("湖北广水");
        orderMaster.setBuyerName("果双成");
        orderMaster.setBuyerOpenid("1111111");
        orderMaster.setBuyerPhone("0722-9527");
        orderMaster.setOrderId("123456");
        orderMaster.setOrderAmount(new BigDecimal(6.6));
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(OrderPayStatusEnums.WAIT.getCode());


        orderMasterRepository.save(orderMaster);
    }
}