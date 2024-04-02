package com.hjk.wangpan.service;

import com.hjk.wangpan.dao.FileMapper;
import com.hjk.wangpan.pojo.FileData;
import com.hjk.wangpan.pojo.FileData;
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
public class FileServiceImpl implements FileService{
    @Autowired
    FileMapper fileMapper;
    @Autowired
    SensitiveFilter sensitiveFilter;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<FileData> getFileAll() {
        List<FileData> fileList = (List<FileData>) redisTemplate.opsForValue().get("filelist");
        if (null == fileList) {
            fileList = fileMapper.getFileAll();
            if(null == fileList){
                redisTemplate.opsForValue().set("filelist", "0", 30, TimeUnit.SECONDS);
            }
        }
        redisTemplate.opsForValue().set("filelist", fileList, 30, TimeUnit.SECONDS);
        return fileList;
    }

    @Override
    public List<FileData> getFileId(int id){
        List<FileData> fileList= (List<FileData>) redisTemplate.opsForValue().get("filelist_"+id);
        if(null==fileList){
            fileList=fileMapper.getFileId(id);
            if(fileList==null){
                redisTemplate.opsForValue().set("filelist_"+id,"0",30,TimeUnit.SECONDS);
            }
        }
        redisTemplate.opsForValue().set("filelist_"+id,fileList,30,TimeUnit.SECONDS);
        return fileList;
    }

    @Override
    public List<FileData> getFileUserId(int id){
        List<FileData> fileList= (List<FileData>) redisTemplate.opsForValue().get("filelist_"+id);
        if(null==fileList){
            fileList=fileMapper.getFileId(id);
            if(fileList==null){
                redisTemplate.opsForValue().set("filelist_"+id,"0",30,TimeUnit.SECONDS);
            }
        }
        redisTemplate.opsForValue().set("filelist_"+id,fileList,30,TimeUnit.SECONDS);
        return fileList;
    }

    @Override
    public int addFile(FileData file){
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        file.setUploadTime(date);
        file.setDownloadSum(0);
        file.setFileLocation(0);
        return fileMapper.addFile(file);
    }

    @Override
    public int deleteFile(int id){
        return fileMapper.deleteFile(id);
    }

    @Override
    public int altFile(FileData file){
        return fileMapper.updateFile(file);
    }

}
