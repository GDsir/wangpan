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
    @Select("SELECT * FROM USERS WHERE AGE=#{age}")
    List<User> getUserAge(int age);

    @Select("SELECT * FROM USERS WHERE ID=#{id}")
    List<User> getUserId(int id);

    @Select("select * from users")
    List<User> getUserAll();

    @Insert("INSERT INTO USERS VALUES (#{id},#{username},#{age},#{phone},#{email})")
    List<User> setUser(int id, String username, int age, BigInteger phone, String email);

    @Delete("DELETE FROM USERS WHERE USERNAME=#{username}")
    List<User> delUser(String username);

    @Update("UPDATE USERS SET age=#{age} WHERE id=#{id}")
    List<User> altUserAge(int id, int age);

    @Insert("insert into users(id,username,age,phone,email) values (#{id},#{username},#{age},#{phone},#{email})")
    List<User> insert(User user);
//    @Select("select * from users")
//    List<User> list();

}