package com.hjk.wangpan.dao;

import com.hjk.wangpan.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper {

    @Select("select * from comment")
    List<Comment> getCommentAll();

    @Select("select * from comment where cmID=#{id}")
    List<Comment> getCommentId(int id);

    @Insert("insert into comment(cmID,cmText,cmAudio,cmTime,cmSupport,userID,postID) values (#{cmID},#{cmText},#{cmAudio},#{cmTime},#{cmSupport},#{userID},#{postID})")
    int insertComment(Comment comment);

    @Delete("delete from comment where cmID=#{id};")
    int deleteComment(int id);

    @Update("update comment set cmSupport=cmSupport+1  where cmID=#{id};")
    int updateCmSupport(int id);

}
