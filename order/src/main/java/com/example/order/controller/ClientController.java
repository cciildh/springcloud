package com.example.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/getMsg")
    public String getProductMsg() {
        //调用方式一：
        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject("http://localhost:8081/msg", String.class);

        //方式二
        ServiceInstance serviceInstance = loadBalancerClient.choose("product-client");
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort() + "/msg");
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }
}
