package com.hjk.wangpan.controller;

import com.hjk.wangpan.pojo.Passage;
import com.hjk.wangpan.service.PassageService;
import com.hjk.wangpan.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PassageController {

    @Autowired
    PassageService passageService;

    @GetMapping("/get/passage")
    public Map<String, Object> getPassageAll() {
        log.info("查询所有文章");
        List<Passage> list = passageService.getPassageAll();
        return StatusCode.success(list);
    }

    @PostMapping("/post/passage")
    public Map<String, Object> addPassage(@RequestBody Passage passage) {
        log.info("添加文章");
        int insert = passageService.addPassage(passage);
        return StatusCode.success(insert);
    }

    @DeleteMapping("/delete/passage/{id}")
    public Map<String, Object> deletePassage(@PathVariable int id) {
        log.info("删除文章");
        int delete = passageService.deletePassage(id);
        return StatusCode.success(delete);
    }

    @PutMapping("/put/passage/views/{id}")
    public Map<String,Object> updatePostPageviews(@PathVariable int id){
        log.info("浏览文章");
        int views= passageService.updatePostPageviews(id);
        return StatusCode.success(views);
    }

    @PutMapping("/put/passage/support/{id}")
    public Map<String,Object> updatePostPageSupport(@PathVariable int id){
        log.info("点赞文章");
        int support= passageService.updatePostPageSupport(id);
        return StatusCode.success(support);
    }


}
