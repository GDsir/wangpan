package com.hjk.wangpan.service;

import com.hjk.wangpan.dao.PassageMapper;
import com.hjk.wangpan.pojo.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class PassageServiceImpl implements PassageService {

    @Autowired
    PassageMapper passageMapper;

    @Override
    public List<Passage> getPassageAll() {
        return passageMapper.getPassageAll();
    }

    @Override
    public int addPassage(Passage passage) {
        passage.setPostPageSupport(0);
        passage.setPostPageviews(0);
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        passage.setPostTime(date);
        return passageMapper.insertPassage(passage);
    }

    @Override
    public int deletePassage(int id) {
        return passageMapper.deletePassage(id);
    }

    @Override
    public int updatePostPageviews(int id){
        return passageMapper.updatePostPageviews(id);
    }

    @Override
    public int updatePostPageSupport(int id){
        return passageMapper.updatePostPageSupport(id);
    }


}