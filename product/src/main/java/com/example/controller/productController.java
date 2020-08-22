package com.example.controller;

import com.example.VO.ProductInfoVO;
import com.example.VO.ProductVO;
import com.example.VO.ResultVO;
import com.example.entity.ProductCategory;
import com.example.entity.ProductInfo;
import com.example.service.PorductInfoService;
import com.example.service.ProductCategoryService;
import com.example.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
}
