package com.example.product.repository;


import com.example.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /**
     * 根据商品类目查询商品目录
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
