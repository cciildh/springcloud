package com.example.repository;

import com.example.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    /**
     * 查询所有在架的商品
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus (Integer productStatus);
}
