package com.ldh.product.client;

import com.ldh.product.common.DecreaseStockInput;
import com.ldh.product.common.ProductInfoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {
    @GetMapping("/msg")
    String productMsg();

    @PostMapping("/product/listForOrder")
    List<ProductInfoOutput> listForOrder(@RequestBody List<String> productId);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(List<DecreaseStockInput> carDTOList);
}
