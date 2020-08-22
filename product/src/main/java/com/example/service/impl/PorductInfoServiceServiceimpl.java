package com.example.service.impl;

import com.example.entity.ProductInfo;
import com.example.enums.ProductStatusEnum;
import com.example.repository.ProductInfoRepository;
import com.example.service.PorductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PorductInfoServiceServiceimpl implements PorductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> getUpAll() {
         return this.productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
}
