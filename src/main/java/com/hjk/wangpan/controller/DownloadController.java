package com.hjk.wangpan.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;

public class DownloadController {

    /**
     * 使用NIO下载文件
     *
     * @param filename
     * @param response
     */
    @GetMapping(value = "/download")
    @ApiOperation("根据文件名下载文件")
    public void download(String filename, HttpServletResponse response) {
        File file = new File(filename);
        if (file.exists()) {
            Path path = file.toPath();
            // 设置响应头
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            // 创建输出流
            try (FileChannel fileChannel = FileChannel.open(path);
                 WritableByteChannel writableByteChannel = Channels.newChannel(response.getOutputStream());) {
                long size = fileChannel.size();
                response.setContentLengthLong(size);
                for (long left = size; left > 0; ) {
                    left -= fileChannel.transferTo((size - left), left, writableByteChannel);
                }
            } catch (IOException e) {
                throw new RuntimeException("文件下载失败，IO错误");
            }
        }

    }

}
