package com.example.service.impl;

import com.example.entity.ProductCategory;
import com.example.repository.ProductCategoryRepository;
import com.example.service.ProductCategoryService;
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
