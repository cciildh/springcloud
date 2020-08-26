package com.ldh.order.service.impl;


import com.ldh.order.dto.OrderDTO;
import com.ldh.order.entity.OrderDetail;
import com.ldh.order.entity.OrderMaster;
import com.ldh.order.enums.OrderPayStatusEnums;
import com.ldh.order.enums.OrderStatusEnums;
import com.ldh.order.repository.OrderDetailRepository;
import com.ldh.order.repository.OrderMasterRepository;
import com.ldh.order.service.OrderService;
import com.ldh.order.utlis.KeyUtli;
import com.ldh.product.client.ProductClient;
import com.ldh.product.common.DecreaseStockInput;
import com.ldh.product.common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String ordeId = KeyUtli.genUniqueKey();
        //1、查询商品服务 获取商品信息 （调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());

        System.out.println("商品ID信息："+ productIdList.toString());
        List<ProductInfoOutput> productInfoOutputList = productClient.listForOrder(productIdList);
        System.out.println("查询的商品信息："+ productInfoOutputList.toString());
        //2、计算总价（调用商品服务）
        //订单详情入库
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfoOutput productInfoOutput : productInfoOutputList) {
                if (orderDetail.getProductId().equals(productInfoOutput.getProductId())) {
                    //单价*数量
                    System.out.println("productInfo:"+productInfoOutput.toString());
                    orderAmount = productInfoOutput.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfoOutput, orderDetail);
                    orderDetail.setOrderId(ordeId);
                    orderDetail.setDetailId(KeyUtli.genUniqueKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //3、扣除库存（调用商品服务）
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);
        //4、订单入库
        OrderMaster orderMaster = new OrderMaster();
        //将买家信息Copy到master对象中
        orderDTO.setOrderId(ordeId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(OrderPayStatusEnums.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
