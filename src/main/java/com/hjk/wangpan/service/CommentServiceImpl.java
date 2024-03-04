package com.hjk.wangpan.service;

import com.hjk.wangpan.dao.CommentMapper;
import com.hjk.wangpan.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentAll() {
        return commentMapper.getCommentAll();
    }

    @Override
    public int addComment(Comment comment) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        comment.setCmSupport(0);
        comment.setCmTime(date);
        return commentMapper.insertComment(comment);
    }

    @Override
    public int deleteComment(int id) {
        return commentMapper.deleteComment(id);
    }

    @Override
    public int updateCmSupport(int id){
        return commentMapper.updateCmSupport(id);
    }

}
