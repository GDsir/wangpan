package com.hjk.wangpan.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileData implements Serializable {
    /**
     *编号
     */
    private int fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 文件位置，文件所在文件夹
     */
    private int fileLocation;
    /**
     * 文件上传时间
     */
    private Date uploadTime;
    /**
     * 文件下载量
     */
    private long downloadSum;
    /**
     * 文件主人
     */
    private int userId;


}
