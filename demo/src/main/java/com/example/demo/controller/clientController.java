package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class clientController {


    @RequestMapping("/getMsg")
    public String getMsg() {
        return "";
    }
}
