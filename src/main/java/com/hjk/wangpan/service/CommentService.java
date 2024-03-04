package com.hjk.wangpan.service;

import com.hjk.wangpan.pojo.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Transactional(rollbackFor = Exception.class)
public interface CommentService {

    List<Comment> getCommentAll();

    int addComment(Comment comment);

    int deleteComment(int id);

    int updateCmSupport(int id);

}
