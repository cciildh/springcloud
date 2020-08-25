package com.example.order.client;

import com.example.order.dto.CarDTO;
import com.example.order.dto.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-client")
public interface ProductServer {
    @GetMapping("/msg")
    String productMsg();

    @PostMapping("/product/listForOrder")
    List<ProductInfo> listForOrder(@RequestBody List<String> productId);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(List<CarDTO> carDTOList);
}
