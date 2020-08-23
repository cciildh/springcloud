package com.example.order.converter;

import com.example.order.dto.OrderDTO;
import com.example.order.entity.OrderDetail;
import com.example.order.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 将前台入参转换为业务组合实体对象
 */
public class ConverterOrderForm2OrderDTO {
    public static OrderDTO convert(OrderForm orderForm)  {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddres());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerPhone(orderForm.getPhone());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            orderDetailList= gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            throw new  RuntimeException("json参数转换错误！");
        }
        orderDTO.setOrderDetailList(orderDetailList);
        System.out.println("订单商品信息："+orderDetailList.size());
        return orderDTO;
    }
}
