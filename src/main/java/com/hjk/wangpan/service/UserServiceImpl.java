package com.hjk.wangpan.service;

//import com.hjk.wangpan.config.Cacheable;

import com.hjk.wangpan.dao.UserMapper;
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
                if (null == userList){
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
    public int updateUserLoginTime(int id) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return userMapper.updateUserLoginTime(id, date);
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