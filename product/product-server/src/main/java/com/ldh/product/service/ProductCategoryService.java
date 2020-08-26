package com.ldh.product.service;


import com.ldh.product.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> ListGetCategoryAll(List<Integer> categoryTypeList);
}
