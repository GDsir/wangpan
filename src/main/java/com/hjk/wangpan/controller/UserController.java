package com.hjk.wangpan.controller;

import com.hjk.wangpan.dao.UserMapper;
import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.service.UserService;
import com.hjk.wangpan.utils.JwtUtils;
import com.hjk.wangpan.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @GetMapping("/get/user/id/{id}")
    public Map<String, Object> getUserId(@PathVariable int id) {
        if (id <= 0) {
            return StatusCode.error("id输入错误");
        }
        log.info("根据id查询用户");
        List<User> listId = userService.getUserId(id);
        return StatusCode.success(listId);
    }

    @GetMapping("/get/user")
    public Map<String, Object> getUserAll() {
        log.info("查询全部用户");
        List<User> list = userService.getUserAll();
        return StatusCode.success(list);
    }

    @PostMapping("/post/user")
    public Map<String, Object> addUser(@RequestBody User user) {
        log.info("注册");
        log.info(user.getUsername());
        if (userService.isUser(user.getUsername()) == 1) {

            return StatusCode.error("用户名已经存在");
        }
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
        if (userService.isUser(user.getUsername()) == 1) {
            return StatusCode.error("用户名已经存在");
        }
        int update = userService.altUser(user);
        return StatusCode.success(update);
    }


    @PostMapping("/post/login")
    public Map<String, Object> login(@RequestBody User user) {
        log.info("登录");
        int flag = userService.isUser(user.getUsername());
        User userLogin;
        if (flag == 1) {
            userLogin = userMapper.getUserUsername(user.getUsername()).get(0);
            if (userLogin.getPwd().equals(user.getPwd())) {
                log.info("登录成功");
                userService.updateUserLoginTime(userLogin.getUsername());
                JwtUtils jwt = JwtUtils.getInstance();
                String token = jwt
                        .setClaim("userid", userLogin.getId())
                        .setClaim("username", userLogin.getUsername())
                        .generateToken();
                redisTemplate.opsForValue().set("userLogin", userLogin.getId(), 30, TimeUnit.SECONDS);
                Map<String, String> tmp = new HashMap<>();
                tmp.put("token", token);
                //将结果存储下来
                return StatusCode.success(tmp);
            } else {
                log.info("密码错误");
                return StatusCode.error("密码错误");
            }
        } else {
            return StatusCode.error("无该用户");
        }
    }


}
