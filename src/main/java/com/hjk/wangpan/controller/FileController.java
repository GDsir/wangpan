package com.hjk.wangpan.controller;


import com.hjk.wangpan.dao.FileMapper;
import com.hjk.wangpan.pojo.FileData;
import com.hjk.wangpan.service.FileService;
import com.hjk.wangpan.pojo.FileData;
import com.hjk.wangpan.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FileController {

    @Autowired
    FileService fileService;
    @Autowired
    FileMapper fileMapper;

    @GetMapping("/get/file")
    public Map<String, Object> getFileAll() {
        List<FileData> list = fileService.getFileAll();
        return StatusCode.success(list);
    }

    @GetMapping("/get/file/id/{id}")
    public Map<String, Object> getFileId(@PathVariable int id) {
        if (id <= 0) {
            return StatusCode.error("id输入错误");
        }
        List<FileData> list = fileService.getFileId(id);
        return StatusCode.success(list);
    }

    @GetMapping("/get/file/user/{userId}")
    public Map<String, Object> getFileUserId(@PathVariable int userId) {
        if (userId <= 0) {
            return StatusCode.error("id输入错误");
        }
        List<FileData> list = fileService.getFileUserId(userId);
        return StatusCode.success(list);
    }

    @PostMapping("/post/file")
    public Map<String, Object> addFile(@RequestBody FileData file) {
        int post = fileService.addFile(file);
        return StatusCode.success(post);
    }

    @DeleteMapping("/delete/file/id/{id}")
    public Map<String, Object> deleteFile(@PathVariable int id) {
        if (id <= 0) {
            return StatusCode.error("id输入错误");
        }
        int delete = fileService.deleteFile(id);
        return StatusCode.success(delete);
    }

    @PutMapping("/put/file")
    public Map<String, Object> altFile(@RequestBody FileData file) {
        int update = fileService.altFile(file);
        return StatusCode.success(update);
    }

}
