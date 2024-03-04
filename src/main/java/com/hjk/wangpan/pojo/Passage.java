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
public class Passage {
    /**
     * 文章标识符
     */
    private int postID;

    /**
     * 文章标题
     */
    private String postTitle;

    /**
     * 文章内容
     */
    private String postText;

    /**
     * 浏览人数
     */
    private int postPageviews;

    /**
     * 文章合成语音URL地址
     */
    private String postAudio;

    /**
     * 文章发表时间
     */
    private Date postTime;

    /**
     * 点赞数
     */
    private int postPageSupport;

    /**
     * 作者用户名
     */
    private String userName;

    /**
     * 作者
     */
    private int userID;

    public Passage() {
        super();
        // TODO Auto-generated constructor stub
    }


    @Override
    public String toString() {
        return "Passage [postID=" + postID + ", postTitle=" + postTitle + ", postText=" + postText + ", postPageviews="
                + postPageviews + ", postAudio=" + postAudio + ", postTime=" + postTime + ", postPageSupport="
                + postPageSupport + ", userID=" + userID + "]";
    }

}
