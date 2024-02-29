package com.hjk.wangpan.service;

import com.hjk.wangpan.pojo.User;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by toutou on 2018/9/15.
 */
public interface UserService {
    List<User> getUser(int age);

    List<User> getUserid(int id);


    List<User> showAll();

    List<User> setUser(int id,String username, int age, BigInteger phone,String email);

    List<User> delUser(String username);

    List<User> altUserAge(int id,int age);

}