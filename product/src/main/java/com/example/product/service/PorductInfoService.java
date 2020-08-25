package com.example.product.service;


import com.example.product.common.DecreaseStockInput;
import com.example.product.entity.ProductInfo;

import java.util.List;

public interface PorductInfoService {
    /**
     * 查询所有在架商品
     *
     * @return
     */
    List<ProductInfo> getUpAll();

    /**
     * 查询商品列表
     *
     * @return
     */
    List<ProductInfo> findList(List<String> productIdList);

    /**
     * 扣库存
     *
     * @param decreaseStockInputList
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);

}
