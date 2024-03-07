package com.hjk.wangpan.service;

import com.hjk.wangpan.dao.CommentMapper;
import com.hjk.wangpan.pojo.Comment;
import com.hjk.wangpan.pojo.Passage;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    SensitiveFilter sensitiveFilter;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<Comment> getCommentAll() {
        List<Comment> commentList = (List<Comment>) redisTemplate.opsForValue().get("commentlist");
        if (null == commentList) {
            commentList = commentMapper.getCommentAll();
        }
        redisTemplate.opsForValue().set("commentlist", commentList, 30, TimeUnit.SECONDS);
        return commentList;
    }

    @Override
    public List<Comment> getCommentId(int id) {
        List<Comment> commentList = (List<Comment>) redisTemplate.opsForValue().get("commentlist_" + id);
        if (null == commentList) {
            commentList = commentMapper.getCommentId(id);
        }
        redisTemplate.opsForValue().set("commentlist_" + id, commentList, 30, TimeUnit.SECONDS);
        return commentList;
    }

    @Override
    public int addComment(Comment comment) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        comment.setCmSupport(0);
        comment.setCmTime(date);
        comment.setCmText(sensitiveFilter.filter(comment.getCmText()));
        return commentMapper.insertComment(comment);
    }

    @Override
    public int deleteComment(int id) {
        return commentMapper.deleteComment(id);
    }

    @Override
    public int updateCmSupport(int id) {
        return commentMapper.updateCmSupport(id);
    }

}
