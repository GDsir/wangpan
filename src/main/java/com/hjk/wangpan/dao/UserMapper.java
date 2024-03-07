package com.hjk.wangpan.dao;

import com.hjk.wangpan.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from users where age=#{age}")
    List<User> getUserAge(int age);

    @Select("select * from users where id=#{id}")
    List<User> getUserId(int id);

    @Select("select * from users")
    List<User> getUserAll();

    @Delete("delete from users where id=#{id}")
    int deleteUser(int id);

    @Update("update users set age=#{age} where id=#{id}")
    int altUserAge(int id, int age);

    @Insert("insert into users(id,username,pwd,age,sex,email,photo,usertype,regtime,logintime) values (#{id},#{username},#{pwd},#{age},#{sex},#{email},#{photo},#{usertype},#{regtime},#{logintime})")
    int insertUser(User user);

    @Update("update users set username=#{username} where id=#{id}")
    int altUserName(int id, String username);

    @Update("update users set logintime=#{logintime} where id=#{id}")
    int updateUserLoginTime(int id, Date logintime);

}