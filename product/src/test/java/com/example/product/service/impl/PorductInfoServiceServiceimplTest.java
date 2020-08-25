package com.example.product.service.impl;

import com.example.product.ProductApplicationTests;
import com.example.product.common.DecreaseStockInput;
import com.example.product.entity.ProductInfo;
import com.example.product.service.PorductInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PorductInfoServiceServiceimplTest extends ProductApplicationTests {

    @Autowired
    private PorductInfoService porductInfoService;

    @Test
    public void findList() {
        List<ProductInfo> productInfoList = porductInfoService.findList(Arrays.asList("164103465734242707"));
        System.out.println(productInfoList.size());
    }

    @Test
    public void decreaseStock() {
        porductInfoService.decreaseStock(Arrays.asList(
                new DecreaseStockInput("164103465734242707",2),
                new DecreaseStockInput("157875196366160022",4)));

    }
}