package com.hjk.wangpan.dao;

import com.hjk.wangpan.pojo.User;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by toutou on 2018/9/15.
 */
@Mapper
public interface UserMapper {
    @Select("SELECT id,username,age,phone,email FROM USERS WHERE AGE=#{age}")
    List<User> getUser(int age);

    @Select("SELECT id,username,age,phone,email FROM USERS WHERE ID=#{id}")
    List<User> getUserid(int id);

    @Select("SELECT id,username,age,phone,email FROM USERS")
    List<User> getUserAll();

    @Insert("INSERT INTO USERS VALUES (#{id},#{username},#{age},#{phone},#{email})")
    List<User> setUser(int id,String username, int age, BigInteger phone, String email);

    @Delete("DELETE FROM USERS WHERE USERNAME=#{username}")
    List<User> delUser(String username);

    @Update("UPDATE USERS SET age=#{age} WHERE id=#{id}" )
    List<User> altUserAge(int id,int age);
}