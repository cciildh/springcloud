package com.ldh.order.controller;

import com.ldh.order.VO.ResultVO;
import com.ldh.order.converter.ConverterOrderForm2OrderDTO;
import com.ldh.order.dto.OrderDTO;
import com.ldh.order.form.OrderForm;
import com.ldh.order.service.OrderService;
import com.ldh.order.utlis.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public ResultVO create(@Valid OrderForm orderForm) {
        OrderDTO orderDTO = ConverterOrderForm2OrderDTO.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            throw  new RuntimeException("订单购物信息为空！");
        }
       OrderDTO result=  orderService.create(orderDTO);

        Map<String,String> map=new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVOUtil.success(map);

    }
}
