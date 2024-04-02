package com.hjk.wangpan.service;

import com.hjk.wangpan.pojo.FileData;
import com.hjk.wangpan.pojo.FileData;

import java.util.List;

public interface FileService {
    List<FileData> getFileAll();

    List<FileData> getFileId(int id);

    List<FileData> getFileUserId(int id);

    int addFile(FileData file);

    int deleteFile(int id);

    int altFile(FileData file);
}
