package com.hjk.wangpan.controller;

import com.hjk.wangpan.pojo.User;
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
    @GetMapping("/hello")
    public String hello() {
        return "SpringBoot";
    }

    @GetMapping("/cs")
    public Map<String,String> cs() {
        Map<String,String> map = new HashMap<>();
        map.put("msg","cs");
        return map;
    }
//    @Autowired
//    UserService userService;
//    @GetMapping("/show")
//    public List<com.hjk.wangpan.pojo.User> getUser(int age){
//        return userService.getUser(age);
//    }
//    @GetMapping("/showid")
//    public List<User> getUserid(int id){
//        return userService.getUserid(id);
//    }


}
