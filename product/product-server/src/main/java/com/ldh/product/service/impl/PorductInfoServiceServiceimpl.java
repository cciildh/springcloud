package com.ldh.product.service.impl;


import com.ldh.product.common.DecreaseStockInput;
import com.ldh.product.common.ProductInfoOutput;
import com.ldh.product.entity.ProductInfo;
import com.ldh.product.enums.ProductStatusEnum;
import com.ldh.product.repository.ProductInfoRepository;
import com.ldh.product.service.PorductInfoService;
import com.ldh.product.utlis.JsonUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PorductInfoServiceServiceimpl implements PorductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Override
    public List<ProductInfo> getUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());

    }

    @Override
    public List<ProductInfoOutput> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutput output = new ProductInfoOutput();
                    BeanUtils.copyProperties(e, output);
                    return output;
                }).collect(Collectors.toList());
    }


    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList =decreaseStockProcess(decreaseStockInputList);
        //list遍历原始写法
//        List<ProductInfoOutput> productInfoOutputList =new ArrayList<>();
//        for (ProductInfo info:productInfoList){
//            ProductInfoOutput output = new ProductInfoOutput();
//            BeanUtils.copyProperties(info,output);
//            productInfoOutputList.add(output);
//        }
        List<ProductInfoOutput> outputList=productInfoList.stream().map(e->{
            ProductInfoOutput output=new ProductInfoOutput();
            BeanUtils.copyProperties(e,output);
            return output;
        }).collect(Collectors.toList());

        //发送商品消息
        amqpTemplate.convertAndSend("order","productinfokey", JsonUtils.objectToJson(outputList));
    }

    @Transactional
    public List<ProductInfo>  decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = new ArrayList<>();

        for (DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());
            if (!productInfoOptional.isPresent()) {
                throw new RuntimeException("该商品不存在:" + decreaseStockInput.getProductId());
            }
            ProductInfo productInfo = productInfoOptional.get();
            //库存是否足够
            Integer result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            if (result < 0) {
                throw new RuntimeException("该商品库存不够：" + decreaseStockInput.getProductId());
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }

        return productInfoList;


    }
}
