package com.hjk.wangpan.service;

import com.hjk.wangpan.config.Cacheable;
import com.hjk.wangpan.dao.UserMapper;
import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.utils.SensitiveFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SensitiveFilter sensitiveFilter;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<User> getUserAge(int age) {
        return userMapper.getUserAge(age);
    }

    @Override
    public List<User> getUserId(int id) {
        List<User> userList = (List<User>) redisTemplate.opsForValue().get("userlist_" + id);
        if (null == userList) {
            userList = userMapper.getUserId(id);
        }
        redisTemplate.opsForValue().set("userlist_" + id, userList, 30, TimeUnit.SECONDS);
        return userList;
    }

    @Override
    public List<User> getUserAll() {
        List<User> userList = (List<User>) redisTemplate.opsForValue().get("userlist");
        if (null == userList) {
            userList = userMapper.getUserAll();
        }
        redisTemplate.opsForValue().set("userlist", userList, 30, TimeUnit.SECONDS);
        return userList;
    }

    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int altUserAge(int id, int age) {
        return userMapper.altUserAge(id, age);
    }

    @Override
    public int altUserName(int id, String name) {
        return userMapper.altUserName(id, name);
    }

    @Override
    public int addUser(User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        user.setRegtime(date);
        user.setLogintime(date);
        user.setUsername(sensitiveFilter.filter(user.getUsername()));
        return userMapper.insertUser(user);
    }

    public int updateUserLoginTime(int id) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return userMapper.updateUserLoginTime(id, date);
    }

}