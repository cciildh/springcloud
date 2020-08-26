package com.ldh.product.service.impl;

import com.ldh.product.common.DecreaseStockInput;
import com.ldh.product.common.ProductInfoOutput;
import com.ldh.product.service.PorductInfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PorductInfoServiceServiceimplTest {
    @Autowired
    private PorductInfoService porductInfoService;

    @Test
    public void aa() {
        System.out.println("ldh");
    }

    @Test
    public void findList() {
        List<ProductInfoOutput> productInfoList = porductInfoService.findList(Arrays.asList("164103465734242707"));
        System.out.println(productInfoList.toString());
        System.out.println(productInfoList.size());
    }

    @Test
    public void decreaseStock() {
        porductInfoService.decreaseStock(Arrays.asList(
                new DecreaseStockInput("164103465734242707", 2),
                new DecreaseStockInput("157875196366160022", 4)));

    }

}