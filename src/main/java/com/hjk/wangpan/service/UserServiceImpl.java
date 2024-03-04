package com.hjk.wangpan.service;

import com.hjk.wangpan.dao.UserMapper;
import com.hjk.wangpan.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUserAge(int age) {
        return userMapper.getUserAge(age);
    }

    @Override
    public List<User> getUserId(int id) {
        return userMapper.getUserId(id);
    }

    @Override
    public List<User> getUserAll() {
        return userMapper.getUserAll();
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
        return userMapper.insertUser(user);
    }

    public int updateUserLoginTime(int id) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return userMapper.updateUserLoginTime(id, date);
    }


}