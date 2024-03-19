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

    @GetMapping("/get/user/id/{id}")
    public Map<String, Object> getUserId(@PathVariable int id) {
        if (id <= 0) {
            return StatusCode.error("id输入错误");
        }
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
        if (id <= 0) {
            return StatusCode.error("id输入错误");
        }
        log.info("根据id删除用户");
        int delete = userService.deleteUser(id);
        return StatusCode.success(delete);
    }

    @PutMapping("/put/user")
    public Map<String, Object> altUser(@RequestBody User user) {
        log.info("根据id更改用户信息");
        int update = userService.altUser(user);
        return StatusCode.success(update);
    }

    @PutMapping("/put/user/login/{id}")
    public Map<String, Object> updateUserLoginTime(@PathVariable int id) {
        if (id <= 0) {
            return StatusCode.error("id输入错误");
        }
        log.info("登录，将logintime改为当前时间");
        int login = userService.updateUserLoginTime(id);
        return StatusCode.success(login);
    }

}
