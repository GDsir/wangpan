package com.hjk.wangpan.service;

import com.hjk.wangpan.dao.UserMapper;
import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by toutou on 2018/9/15.
 */
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

    @Override
    public List<User> setUser(int id, String username, int age, BigInteger phone, String email) {
        return userMapper.setUser(id, username, age, phone, email);
    }

    @Override
    public List<User> delUser(String username) {
        return userMapper.delUser(username);
    }

    @Override
    public List<User> altUserAge(int id, int age) {
        return userMapper.altUserAge(id, age);
    }

    @Override
    public List<User> addUser(User user) {
        return userMapper.insert(user);
    }

//    @Override
//    public List<User> list(){
//        return userMapper.list();
//    }

}