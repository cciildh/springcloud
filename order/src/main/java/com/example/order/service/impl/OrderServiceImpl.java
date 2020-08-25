package com.example.order.service.impl;

import com.example.order.client.ProductServer;
import com.example.order.dto.CarDTO;
import com.example.order.dto.OrderDTO;
import com.example.order.dto.ProductInfo;
import com.example.order.entity.OrderDetail;
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
    private ProductServer productServer;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String ordeId = KeyUtli.genUniqueKey();
        //1、查询商品服务 获取商品信息 （调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());

        System.out.println("商品ID信息："+ productIdList.toString());
        List<ProductInfo> productInfoList = productServer.listForOrder(productIdList);
        System.out.println("查询的商品信息："+ productInfoList.toString());
        //2、计算总价（调用商品服务）
        //订单详情入库
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfo productInfo : productInfoList) {
                if (orderDetail.getProductId().equals(productInfo.getProductId())) {
                    //单价*数量
                    System.out.println("productInfo:"+productInfo.toString());
                    orderAmount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(ordeId);
                    orderDetail.setDetailId(KeyUtli.genUniqueKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //3、扣除库存（调用商品服务）
        List<CarDTO> carDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CarDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productServer.decreaseStock(carDTOList);
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
