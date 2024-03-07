package com.hjk.wangpan.controller;

import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hjk.wangpan.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Orall
 * @version 1.0
 * @description
 * @data 2022-9-11
 */
@RestController
public class HelloController {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @GetMapping("/hello")
    public String hello() {
        return "SpringBoot";
    }

    @GetMapping("/tes")
    public String test() {
        String test = "大傻逼,1234";
        test = sensitiveFilter.filter(test);
        return test;
    }

    @GetMapping("/cs")
    public Map<String, String> cs() {
        Map<String, String> map = new HashMap<>();
        map.put("msg", "cs");
        return map;
    }

}
