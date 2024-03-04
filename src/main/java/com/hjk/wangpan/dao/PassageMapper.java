package com.hjk.wangpan.dao;

import com.hjk.wangpan.pojo.Passage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PassageMapper {

    @Select("select * from passage")
    List<Passage> getPassageAll();

    @Insert("insert into passage(postID,postTitle,postText,postPageviews,postAudio,postTime,postPageSupport,userID) values (#{postID},#{postTitle},#{postText},#{postPageviews},#{postAudio},#{postTime},#{postPageSupport},#{userID})")
    int insertPassage(Passage passage);

    @Delete("delete from passage where postID=#{id};")
    int deletePassage(int id);

    @Update("update passage set postPageviews = postPageviews+1 where postID=#{id};")
    int updatePostPageviews(int id);

    @Update("update passage set postPageSupport = postPageSupport+1 where postID=#{id};")
    int updatePostPageSupport(int id);

}
