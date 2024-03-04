package com.hjk.wangpan.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {
    /**
     * 评论标识符
     */
    private int cmID;

    /**
     * 评论合成语音URL地址
     */
    private String cmAudio;

    /**
     * 评论时间
     */
    private Date cmTime;

    /**
     * 评论内容
     */
    private String cmText;

    /**
     * 评论获赞数
     */
    private int cmSupport;

    /**
     * 评论文章
     */
    private int postID;

    /**
     * 评论者
     */
    private int userID;

    public Comment() {
    }


    @Override
    public String toString() {
        return "Comment [cmID=" + cmID + ", cmAudio=" + cmAudio + ", cmTime=" + cmTime + ", cmText=" + cmText
                + ", cmSupport=" + cmSupport + ", userID=" + userID + "]";
    }


}
