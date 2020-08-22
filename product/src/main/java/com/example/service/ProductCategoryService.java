package com.example.service;

import com.example.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> ListGetCategoryAll(List<Integer> categoryTypeList);
}
