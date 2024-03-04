package com.hjk.wangpan.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    /**
     * 编号
     */
    private int id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String pwd;

    /**
     * 用户年龄
     */
    private Integer age;


    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像地址
     */
    private String photo;

    /**
     * 用户角色标识：普通用户/管理员
     */
    private String usertype;

    /**
     * 用户注册时间
     */
    private Date regtime;

    /**
     * 用户登录时间
     */
    private Date logintime;

    public User() {
        super();
    }

    @Override
    public String toString() {
        return "User [userName=" + username + ", pwd=" + pwd + ", regAge=" + age + ", regSex=" + sex
                + ", regEmail=" + email + ", regPhoto=" + photo + ", userType=" + usertype + ", regTime=" + regtime
                + ", loginTime=" + logintime + "]";
    }

}