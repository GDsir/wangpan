package com.hjk.wangpan.dao;

import com.hjk.wangpan.pojo.FileData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import com.hjk.wangpan.pojo.FileData;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FileMapper {

    @Select("select * from file")
    List<FileData> getFileAll();

    @Select("select * from file where fileId=#{fileId}")
    List<FileData> getFileId(int fileId);

    @Select("select * from file where userId=#{userId} ")
    List<FileData> getFileUserId(int userId);

    @Insert("insert into file(fileId,fileName,fileSuffix,uploadTime,fileLocation,userId,downloadSum) values (#{fileId},#{fileName},#{fileSuffix},#{uploadTime},#{fileLocation},#{userId},#{downloadSum})")
    int addFile(FileData fileData);

    @Delete("delete from file where fileId=#{fileId}")
    int deleteFile(int fileId);

    @Update("update file set fileName=#{fileName},fileSuffix=#{fileSuffix},uploadTime=#{uploadTime},fileLocation=#{fileLocation},userId=#{userId},downloadSum=#{downloadSum} where fileId=#{fileId}")
    int updateFile(FileData fileData);


}
