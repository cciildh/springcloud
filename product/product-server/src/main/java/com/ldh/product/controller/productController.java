package com.ldh.product.controller;


import com.ldh.product.VO.ProductInfoVO;
import com.ldh.product.VO.ProductVO;
import com.ldh.product.VO.ResultVO;
import com.ldh.product.common.DecreaseStockInput;
import com.ldh.product.common.ProductInfoOutput;
import com.ldh.product.entity.ProductCategory;
import com.ldh.product.entity.ProductInfo;
import com.ldh.product.service.PorductInfoService;
import com.ldh.product.service.ProductCategoryService;
import com.ldh.product.utlis.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class productController {
    @Autowired
    private PorductInfoService porductInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/getUpAll")
    public ResultVO getUpAll() {

        // 查询所有在架的商品
        List<ProductInfo> porductInfolist = porductInfoService.getUpAll();

        // 获取类目TYPE列表(获取list实体类中的某个字段集合)
        List<Integer> categoryTypeList = porductInfolist.stream().map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());

        // 从数据库查询类目
        List<ProductCategory> CategoryList = productCategoryService.ListGetCategoryAll(categoryTypeList);

        // 构造json 数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory category : CategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : porductInfolist) {
                if (productInfo.getCategoryType().equals(category.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);

    }

    @PostMapping("/listForOrder")
    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList) {
        return porductInfoService.findList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        porductInfoService.decreaseStock(decreaseStockInputList);
    }
}
