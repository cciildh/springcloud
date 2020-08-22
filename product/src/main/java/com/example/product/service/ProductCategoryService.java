package com.example.product.service;


import com.example.product.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> ListGetCategoryAll(List<Integer> categoryTypeList);
}
