package com.hjk.wangpan.controller;

import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.service.UserService;
import com.hjk.wangpan.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/get/user/age/{age}")
    public Map<String, Object> getUserAge(@PathVariable int age) {
        log.info("根据年龄查询用户");
        List<User> listage = userService.getUserAge(age);
        return StatusCode.success(listage);
    }

    @GetMapping("/get/user/id/{id}")
    public Map<String, Object> getUserId(@PathVariable int id) {
        log.info("根据id查询用户");
        List<User> listid = userService.getUserId(id);
        return StatusCode.success(listid);
    }


    @GetMapping("/get/user")
    public Map<String, Object> getUserAll() {
        log.info("查询全部用户");
        List<User> list = userService.getUserAll();
        return StatusCode.success(list);
    }

    @PostMapping("/post/user")
    public Map<String, Object> addUser(@RequestBody User user) {
        log.info("添加用户");
        int post = userService.addUser(user);
        return StatusCode.success(post);
    }

    @DeleteMapping("/delete/user/id/{id}")
    public Map<String, Object> deleteUser(@PathVariable int id) {
        log.info("根据id删除用户");
        int delete = userService.deleteUser(id);
        return StatusCode.success(delete);
    }

    @PutMapping("/put/user/id/age")
    public Map<String, Object> altUserAge(int id, int age) {
        log.info("根据id更改用户年龄");
        int update = userService.altUserAge(id, age);
        return StatusCode.success(update);
    }

    @PutMapping("/put/user/id/username")
    public Map<String, Object> altUserName(int id, String username) {
        log.info("根据id更改用户名字");
        int update = userService.altUserName(id, username);
        return StatusCode.success(update);
    }

    @PutMapping("/put/user/login/{id}")
    public Map<String, Object> updateUserLoginTime(@PathVariable int id) {
        log.info("登录，将logintime改为当前时间");
        int login = userService.updateUserLoginTime(id);
        return StatusCode.success(login);
    }

}
