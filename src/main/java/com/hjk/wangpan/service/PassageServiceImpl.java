package com.hjk.wangpan.service;

import com.hjk.wangpan.dao.PassageMapper;
import com.hjk.wangpan.pojo.Passage;
import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.utils.SensitiveFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PassageServiceImpl implements PassageService {

    @Autowired
    PassageMapper passageMapper;
    @Autowired
    SensitiveFilter sensitiveFilter;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<Passage> getPassageAll() {
        List<Passage> passageList = (List<Passage>) redisTemplate.opsForValue().get("passagelist");
        if (null == passageList) {
            passageList = passageMapper.getPassageAll();
            if (passageList == null) {
                redisTemplate.opsForValue().set("passagelist", "0", 30, TimeUnit.SECONDS);
            }

        }
        redisTemplate.opsForValue().set("passagelist", passageList, 30, TimeUnit.SECONDS);
        return passageList;
    }

    @Override
    public List<Passage> getPassageId(int id) {
        List<Passage> passageList = (List<Passage>) redisTemplate.opsForValue().get("passagelist_" + id);
        if (null == passageList) {
            try {
                boolean isLock = tryLock("passagelist_" + id);
                while (!isLock) {
                    Thread.sleep(50);
                    return getPassageId(id);
                }
                passageList = (List<Passage>) redisTemplate.opsForValue().get("passagelist_" + id);
                if (null == passageList){
                    passageList = passageMapper.getPassageId(id);
                    Thread.sleep(200);
                    if (null == passageList) {
                        log.info("查询无该文章");
                        redisTemplate.opsForValue().set("passagelist_" + id, "0", 30, TimeUnit.SECONDS);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                unLock("passagelist_" + id);
            }
        }
        redisTemplate.opsForValue().set("passagelist_" + id, passageList, 30, TimeUnit.SECONDS);
        return passageList;
    }

    @Override
    public int addPassage(Passage passage) {
        passage.setPostPageSupport(0);
        passage.setPostPageviews(0);
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        passage.setPostTime(date);
        passage.setPostText(sensitiveFilter.filter(passage.getPostText()));
        passage.setPostTitle(sensitiveFilter.filter(passage.getPostTitle()));
        return passageMapper.insertPassage(passage);
    }

    @Override
    public int altPassage(Passage passage) {
        return passageMapper.altPassage(passage);
    }

    @Override
    public int deletePassage(int id) {
        return passageMapper.deletePassage(id);
    }

    @Override
    public int updatePostPageviews(int id) {
        return passageMapper.updatePostPageviews(id);
    }

    @Override
    public int updatePostPageSupport(int id) {
        return passageMapper.updatePostPageSupport(id);
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