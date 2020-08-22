package com.example.product.service.impl;

import com.example.product.entity.ProductCategory;
import com.example.product.repository.ProductCategoryRepository;
import com.example.product.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductCategoryServiceimpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Override
    public List<ProductCategory> ListGetCategoryAll(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
