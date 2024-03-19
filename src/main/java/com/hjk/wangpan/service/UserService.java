package com.hjk.wangpan.service;

import com.hjk.wangpan.pojo.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Transactional(rollbackFor = Exception.class)

public interface UserService {

    List<User> getUserId(int id);

    List<User> getUserAll();

    int deleteUser(int id);

    int altUser(User user);

    int addUser(User user);

    int updateUserLoginTime(int id);

}