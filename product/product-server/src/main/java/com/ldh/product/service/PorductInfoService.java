package com.ldh.product.service;

import com.ldh.product.common.DecreaseStockInput;
import com.ldh.product.common.ProductInfoOutput;
import com.ldh.product.entity.ProductInfo;

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
    List<ProductInfoOutput> findList(List<String> productIdList);

    /**
     * 扣库存
     *
     * @param decreaseStockInputList
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);

}
