package com.hjk.wangpan.controller;

import com.hjk.wangpan.pojo.Comment;
import com.hjk.wangpan.service.CommentService;
import com.hjk.wangpan.service.CommentServiceImpl;
import com.hjk.wangpan.service.PassageService;
import com.hjk.wangpan.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/get/comment")
    public Map<String, Object> getCommentAll() {
        log.info("查询所有评论");
        List<Comment> list = commentService.getCommentAll();
        return StatusCode.success(list);
    }

    @PostMapping("/post/comment")
    public Map<String, Object> addComment(@RequestBody Comment comment) {
        log.info("添加评论");
        int insert = commentService.addComment(comment);
        return StatusCode.success(insert);
    }

    @DeleteMapping("/delete/comment/{id}")
    public Map<String, Object> deleteComment(@PathVariable int id) {
        log.info("删除评论");
        int delete = commentService.deleteComment(id);
        return StatusCode.success(delete);
    }

    @PutMapping("/put/comment/support/{id}")
    public Map<String,Object> updateCmSupport(@PathVariable int id){
        log.info("点赞评论");
        int support = commentService.updateCmSupport(id);
        return StatusCode.success(support);
    }

}
