package com.hjk.wangpan.service;

import com.hjk.wangpan.pojo.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by toutou on 2018/9/15.
 */
public interface UserService {
    List<User> getUserAge(int age);

    List<User> getUserId(int id);


    List<User> getUserAll();

//    List<User> setUser(int id, String username, int age, BigInteger phone, String email);

    //    List<User> delUser(String username);
    List<User> deleteUser(int id);

    List<User> altUserAge(int id, int age);


    List<User> addUser(User user);
//    List<User> list();

}