package com.hjk.wangpan.service;

//import com.hjk.wangpan.config.Cacheable;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjk.wangpan.dao.UserMapper;
//import com.hjk.wangpan.dto.UserDTO;
import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.utils.SensitiveFilter;
import com.hjk.wangpan.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.relational.repository.Lock;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SensitiveFilter sensitiveFilter;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<User> getUserId(int id) {
        List<User> userList = (List<User>) redisTemplate.opsForValue().get("userlist_" + id);
        if (null == userList) {
            try {
                boolean isLock = tryLock("user_" + id);
                while (!isLock) {
                    Thread.sleep(50);
                    return getUserId(id);
                }
                userList = (List<User>) redisTemplate.opsForValue().get("userlist_" + id);
                if (null == userList) {
                    userList = userMapper.getUserId(id);
                    Thread.sleep(200);
                    if (null == userList) {
                        log.info("查询无该用户");
                        redisTemplate.opsForValue().set("userlist_" + id, "0", 30, TimeUnit.SECONDS);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                unLock("user_" + id);
            }
        }
        redisTemplate.opsForValue().set("userlist_" + id, userList, 30, TimeUnit.SECONDS);
        return userList;
    }

    @Override
    public List<User> getUserAll() {
        List<User> userList = (List<User>) redisTemplate.opsForValue().get("userlist");
        if (null == userList) {
            userList = userMapper.getUserAll();
            if (null == userList) {
                log.info("无用户");
                redisTemplate.opsForValue().set("userlist", "0", 30, TimeUnit.SECONDS);
            }
        }
        redisTemplate.opsForValue().set("userlist", userList, 30, TimeUnit.SECONDS);
        return userList;
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int altUser(User user) {
        return userMapper.altUser(user);
    }

    @Override
    public int addUser(User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        user.setRegtime(date);
        user.setLogintime(date);
        user.setUsername(sensitiveFilter.filter(user.getUsername()));
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUserLoginTime(String username) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return userMapper.updateUserLoginTime(username, date);
    }

    @Override
    public int isUser(String username) {
        List<User> userList = (List<User>) redisTemplate.opsForValue().get("userlist_" + username);
        if (null == userList) {
            try {
                boolean isLock = tryLock("user_" + username);
                while (!isLock) {
                    Thread.sleep(50);
                    return isUser(username);
                }
                userList = (List<User>) redisTemplate.opsForValue().get("userlist_" + username);
                if (null == userList) {
                    userList = userMapper.getUserUsername(username);
                    Thread.sleep(200);
                    if (null == userList) {
                        log.info("查无用户名");
                        return 0;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                unLock("user_" + username);
            }
        }
        redisTemplate.opsForValue().set("userlist_" + username, userList, 30, TimeUnit.SECONDS);

        return 1;
    }


    private boolean tryLock(String key) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(flag);
    }

    //释放锁
    private void unLock(String key) {
        redisTemplate.delete(key);
    }


}