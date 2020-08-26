package com.ldh.order.controller;

import com.ldh.order.converter.ConverterOrderForm2OrderDTO;
import com.ldh.order.dto.OrderDTO;
import com.ldh.order.entity.OrderDetail;
import com.ldh.order.form.OrderForm;
import com.ldh.product.client.ProductClient;
import com.ldh.product.common.ProductInfoOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试服务注册与发现
 */
@RestController
public class ClientController {
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private ProductClient productClient;


    @RequestMapping("/getMsg")
    public String getProductMsg() {
        //调用方式一：（直接restTemplate写死url）
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject("http://localhost:8081/msg", String.class);

        //方式二(使用loadBalancerClient 获取url)
        //使用tspring:application:name: server-name 来获取服务名为product-client的下的服务
//        ServiceInstance serviceInstance = loadBalancerClient.choose("product-client");
//        //为product-client中的一个服务
//        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort() + "/msg");
//        String result = restTemplate.getForObject(url, String.class);

        //方式三（利用@LoadBalanced注解，可在restTemplate里面使用应用名字）
        String result = productClient.productMsg();
        return result;
    }

    @RequestMapping("/tst1")
    public List<ProductInfoOutput> productInfoList(@Valid OrderForm orderForm) {
        OrderDTO orderDTO = ConverterOrderForm2OrderDTO.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            throw new RuntimeException("订单购物信息为空！");
        }
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());

        System.out.println("前台数据" + productIdList.toString());
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);

        return productInfoList;
    }
}
