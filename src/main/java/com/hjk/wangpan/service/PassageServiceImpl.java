package com.hjk.wangpan.service;

import com.hjk.wangpan.dao.PassageMapper;
import com.hjk.wangpan.pojo.Passage;
import com.hjk.wangpan.pojo.User;
import com.hjk.wangpan.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
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
        }
        redisTemplate.opsForValue().set("passagelist", passageList, 30, TimeUnit.SECONDS);
        return passageList;
    }

    @Override
    public List<Passage> getPassageId(int id) {
        List<Passage> passageList = (List<Passage>) redisTemplate.opsForValue().get("passagelist_" + id);
        if (null == passageList) {
            passageList = passageMapper.getPassageId(id);
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

}