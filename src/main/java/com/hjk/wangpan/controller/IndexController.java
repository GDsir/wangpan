package com.hjk.wangpan.controller;

import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by toutou on 2018/9/15.
 */
@RestController
//@RequestMapping(value = "/api/")

public class IndexController {
    @Autowired
    UserService userService;
    @GetMapping("/show")
    public List<User> getUser(int age){
        return userService.getUser(age);
    }
    @GetMapping("/showid")
    public List<User> getUserid(int id){
        return userService.getUserid(id);
    }
    @GetMapping("/showall")
    public List<User> getUserall(){
        return userService.showAll();
    }
    @GetMapping("/setuser")
    public List<User> setUser(int id,String username, int age, BigInteger phone, String email){
        return userService.setUser(id,username, age, phone, email);
    }
    @GetMapping("/deluser")
    public List<User> delUser(String username){
        return userService.delUser(username);
    }
    @GetMapping("/altuser")
    public List<User> altUserAge(int id, int age){
        return userService.altUserAge(id, age);
    }




    @GetMapping("/index")
    public Map<String, String> Index(){
        Map map = new HashMap<String, String>();
        map.put("北京","北方城市");
        map.put("深圳","南方城市");
        return map;
    }
}