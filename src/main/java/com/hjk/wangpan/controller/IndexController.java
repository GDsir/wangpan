package com.hjk.wangpan.controller;

import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.service.UserService;
import com.hjk.wangpan.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
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

    //    StatusCode statusCode;
    @GetMapping("/get/users/age/{age}")
    public Map<String, Object> getUserAge(@PathVariable int age) {
        List<User> listage;
        try {
            listage = userService.getUserAge(age);
            if (age <= 0) {
                return StatusCode.error("输入年龄错误");
            }
        } catch (Exception e) {
            return StatusCode.error(1001);
        }
        return StatusCode.success(listage);
    }


    //    @GetMapping("/test/get/users/id")
//    public List<User> getUserid(int id){
//        return userService.getUserid(id);
//    }
    @GetMapping("/get/users/id/{id}")
    public Map<String, Object> getUserId(@PathVariable int id) {
        List<User> listid;
        try {
            listid = userService.getUserId(id);
            if (id <= 0) {
                return StatusCode.error("输入id错误");
            } else if (listid.isEmpty()) {
                return StatusCode.error("无用户");
            }
        } catch (Exception e) {
            return StatusCode.error(1001);
        }
        return StatusCode.success(listid);
    }

    //    @GetMapping("/test/get/users")
//    public Map<String, Object> getUserAll() {
//        List<User> list = userService.getUserAll();
//        return StatusCode.success(list);
//    }
    @GetMapping("/get/users")
    public Map<String, Object> getUserAll() {
//        log.info("查询全部数据");
        List<User> list;
        try {
            list = userService.getUserAll();
        } catch (Exception e) {
            return StatusCode.error(1001);
        }
        return StatusCode.success(list);
    }

    @PostMapping("/post/user")
    public Map<String, Object> addUser(@RequestBody User user) {
        List<User> post;
        try {
            post = userService.addUser(user);
        } catch (Exception e) {
            return StatusCode.error(1001);
        }
        return StatusCode.success(post);
    }


//    @PostMapping("/test/post/user")
//    public List<User> setUser(int id, String username, int age, BigInteger phone, String email) {
//        return userService.setUser(id, username, age, phone, email);
//    }

    @GetMapping("/delete/user/username")
    public List<User> delUser(String username) {
        return userService.delUser(username);
    }

    @GetMapping("/put/user/id/age")
    public List<User> altUserAge(int id, int age) {
        return userService.altUserAge(id, age);
    }


    @GetMapping("/index")
    public Map<String, String> Index() {
        Map map = new HashMap<String, String>();
        map.put("北京", "北方城市");
        map.put("深圳", "南方城市");
        return map;
    }
}