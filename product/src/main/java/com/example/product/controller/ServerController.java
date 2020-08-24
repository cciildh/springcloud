package com.example.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @RequestMapping("/msg")
    public  String msg(){
        return "这是 Product 的一个 msg 服务";
    }
}
