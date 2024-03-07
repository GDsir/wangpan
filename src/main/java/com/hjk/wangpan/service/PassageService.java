package com.hjk.wangpan.service;

import com.hjk.wangpan.pojo.Passage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Transactional(rollbackFor = Exception.class)
public interface PassageService {

    List<Passage> getPassageAll();

    List<Passage> getPassageId(int id);

    int addPassage(Passage passage);

    int deletePassage(int id);

    int updatePostPageviews(int id);

    int updatePostPageSupport(int id);

}
