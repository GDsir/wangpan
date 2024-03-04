package com.hjk.wangpan.service;

import com.hjk.wangpan.pojo.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Transactional(rollbackFor = Exception.class)

public interface UserService {
    List<User> getUserAge(int age);

    List<User> getUserId(int id);


    List<User> getUserAll();

//    List<User> setUser(int id, String username, int age, BigInteger phone, String email);

    //    List<User> delUser(String username);
    int deleteUser(int id);

    int altUserAge(int id, int age);

    int altUserName(int id, String name);

    int addUser(User user);

    int updateUserLoginTime(int id);

}