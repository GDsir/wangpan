package com.hjk.wangpan.controller;


import com.alibaba.fastjson.JSONObject;
//import com.project.evaluate.annotation.RateLimiter;
//import com.hjk.wangpan.utils.FileUploadUtil;
//import com.project.evaluate.util.response.ResponseResult;
//import com.project.evaluate.util.response.ResultCode;
import com.hjk.wangpan.utils.StatusCode;
import io.jsonwebtoken.lang.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author Levi
 * @version 1.0 (created by Spring Boot)
 * @description 文件上传模块，必须保证servlet.multipart.enabled=false，否则无法获取到文件域
 * @since 2022/12/16 15:22
 */
@RequestMapping("/api/common/file")
@Controller
@CrossOrigin("*")
@PropertySource("classpath:application.properties")
@Slf4j
@Api(tags = "upload-文件上传")
class UploadController {
    // 图片后缀集合
    private static final Set<String> IMAGE_SUFFIX_SET = new HashSet<String>(Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp"));

    //    编码格式
    @Value("${file.character-set}")
    private String character;

    //    缓存文件前缀
    @Value("${file.temp-prefix}")
    private String tempPrefix;

    private String tempPrePath;

    private String imagePrePath;

    @Value("#{systemProperties['user.dir']}")
    private String userDir;

    //    缓冲区大小阈值
    @Value("${file.threshold-size}")
    private String sizeThreshold;

    //    文件分片最大值
    @Value("${file.file-size-max}")
    private String fileSizeMax;

    //      请求最大值
    @Value("${file.request-size-max}")
    private String requestSizeMax;
    /**
     * 图片上传前缀
     */
    @Value("${file.image-prefix}")
    private String imagePrefix;

    private static void deleteFile(int index, String name, String filePath) {
        for (int i = 0; i < index; i++) {
            File file = new File(filePath, i + "_" + name);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @ApiOperation("批量上传文件")
    @RequestMapping(value = "/upload/page")
//    @ResponseBody
//    @RateLimiter(value = 100, timeout = 1000)
    public Map<String, Object> upload(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
//        初始化参数
        this.init();
//        设置编码格式
        response.setCharacterEncoding(this.character);
        log.info("文件上传开始：初始化参数以及设置编码格式：{}", this.character);
//        初始化变量
        Integer schunk = null; // 当前分片编号
        Integer schunks = null; // 总分片数
        String filename = null; // 文件名
        String filePath = this.tempPrePath; // 文件前缀路径
        BufferedOutputStream os = null; // 输出流
        log.info("初始化变量：filaPath:{}", filePath);


        try {
//            用于处理接受到的文件类
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(Integer.parseInt(this.sizeThreshold)); // 文件缓冲区大小
            factory.setRepository(new File(filePath)); // 设置文件缓冲区路径
            log.info("处理接收到的文件类、设置文件缓冲区大小、设置文件缓冲区路径");
//            解析request中的文件信息
            ServletFileUpload upload = new ServletFileUpload(factory);
            System.out.println(upload.toString());
            upload.setFileSizeMax(Long.parseLong(this.fileSizeMax));
            upload.setSizeMax(Long.parseLong(this.requestSizeMax));
            System.out.println(upload.toString());
            log.info("解析request中的文件信息，设置参数：fileSizeMax-{}，requestSizeMax-{}", fileSizeMax, requestSizeMax);
//            解析这个文件
            log.info(request.getQueryString());
            log.info(request.getRequestURI());
            log.info("qaq" + request.getContentLength());
            List<FileItem> items = upload.parseRequest(request);
            System.out.println("Qaq" + items.size());
//            取出文件信息
            log.info("---------------------------------------------------------------------------------------------");
            for (FileItem item : items) {
                if (item.isFormField()) {
//                    获取当前分片序号
                    if ("chunk".equals(item.getFieldName())) {
                        schunk = Integer.parseInt(item.getString(this.character));
                    }
//                    获取总分片数
                    if ("chunks".equals(item.getFieldName())) {
                        schunks = Integer.parseInt(item.getString(this.character));
                    }

//                    获取文件名
                    if ("name".equals(item.getFieldName())) {
                        filename = item.getString(this.character);
                        System.out.println("qaq " + filename);
                    }

                    log.info("分片序号：{}，总分片数：{}，文件名：{}", schunk, schunk, filename);
                }
            }
            if (filename == null) {
                filename = UUID.randomUUID().toString();
            }
            String duplicateFile = userDir + tempPrefix + "\\" + filename;
            log.info("多余文件名:{}", duplicateFile);
            if (cn.hutool.core.io.FileUtil.exist(duplicateFile)) {
                log.info("正在删除文件！！");
                System.gc();
                cn.hutool.core.io.FileUtil.del(duplicateFile);
                log.info("文件删除成功，重新上传！！");
                System.gc();
            }

            log.info("---------------------------------------------------------------------------------------------");
//            System.out.println("上传文件：文件解析完成");
//            取出文件
            for (FileItem item : items) {
                if (!item.isFormField()) {
//                    缓存文件名，如果没有分片，则缓存文件名就是文件名
                    String tempFileName = filename;
//                    如果文件名存在，且含有分片，则说明可以存储下来
                    if (filename != null) {
                        if (schunk != null) {
//                            缓存文件名字：分片序号_文件名
                            tempFileName = schunk + '_' + filename;
                        }
                        File file = new File(this.tempPrePath, tempFileName);
//                        如果文件不存在则需要存下来
                        if (!file.exists()) {
                            item.write(file);
                        }
                    }
                }
            }
            log.info("文件上传完成，开始合并文件");
            //            合并文件：有分片并且已经到了最后一个分片才需要合并 todo:没有分片的时候应该处理一下
            if (schunks != null && schunk.intValue() == schunks.intValue() - 1) {
//                合并文件之后的路径
                File tempFile = new File(filePath, filename);
                log.info("文件合并之后的路径：{}", tempFile.getAbsolutePath());
                os = new BufferedOutputStream(new FileOutputStream(tempFile));
//                是否能够找到分片文件的标记
                boolean isExist = true;
//                找出所有的分片
                for (int i = 0; i < schunks; i++) {
                    File file = new File(filePath, i + '_' + filename);
                    int j = 0;
                    while (!file.exists()) {
                        log.info("等待文件，第{}次等待", j);
                        Thread.sleep(1000);
//                        如果超过了60秒还没有找到那些分片，就跳出来，并且将前面所有的分片删除
                        if (j == 60) {
                            UploadController.deleteFile(i, filename, filePath);
                            isExist = false;
                            break;
                        }
                        j++;
                    }
//                    如果读不到分片文件，则跳出循环
                    if (!isExist) {
                        break;
                    }
//                    将分片文件读取到byte数组中
                    byte[] bytes = FileUtils.readFileToByteArray(file);
//                    写入
                    os.write(bytes);
                    os.flush();
//                    删除临时文件
                    file.delete();
                }
                os.flush();
                if (!isExist) {
//                    返回失败信息
                    log.info("上传失败");
                    jsonObject.put("msg", "file upload fail");
                    jsonObject.put("error", "文件上传失败，分片丢失");
                    return StatusCode.error(jsonObject);
//                    return new ResponseResult(ResultCode.IO_OPERATION_ERROR, jsonObject);
                } else {
                    log.info("上传成功：filename：{}", filename);
                    //                返回成功信息
                    jsonObject.put("msg", "file upload success");
                    jsonObject.put("filename", tempFile.getAbsoluteFile());
                    return StatusCode.success(jsonObject);
//                    return new ResponseResult(ResultCode.SUCCESS, jsonObject);
                }
            } else {
                System.out.println("xiaowenhjian");
                // 小文件上传完成
                if (Objects.nonNull(filename)) {
                    File file = new File(tempPrePath, filename);
                    if (file.exists()) {
                        jsonObject.put("msg", "上传成功");
                        jsonObject.put("filename", file.getAbsolutePath());
                        return StatusCode.success(jsonObject);
//                        return new ResponseResult(ResultCode.SUCCESS, jsonObject);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("upload 模块 失败");
        } finally {
            /*
                关闭流
            */
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
//                    System.out.println("upload 模块 os 关闭失败");
                    throw new RuntimeException("upload 模块 os 关闭失败");
                }
            }
        }
        log.info("不可到达区域");
        jsonObject.put("msg", "file upload failed");
        jsonObject.put("filename", null);
        return StatusCode.success(jsonObject);
    }

    private void init() {
        //        初始化参数
        if (!Strings.hasText(this.character)) {
            this.character = "UTF-8";
        }
        if (!Strings.hasText(this.sizeThreshold)) {
            this.sizeThreshold = "1024";
        }
        if (!Strings.hasText(this.fileSizeMax)) {
            Long number = 10L * 1024L * 1024L * 1024L;
            this.fileSizeMax = String.valueOf(number);
        }
        if (!Strings.hasText(this.requestSizeMax)) {
            Long number = 4000L * 1024L * 1024L * 1024L;
            this.requestSizeMax = String.valueOf(number);
        }
        // 如果临时路径为空，则建立在运行文件下面的temp文件夹中
        if (!Strings.hasText(this.tempPrefix)) {
            this.tempPrePath = userDir + File.separator + "temp";
        } else {
            this.tempPrePath = userDir + File.separator + this.tempPrefix;
        }
        // 如果图片路径为空，则建立在运行文件下面的picture文件夹中
        if (!Strings.hasText(imagePrefix)) {
            this.imagePrePath = userDir + File.separator + "static/picture";
        } else {
            this.imagePrePath = userDir + File.separator + this.imagePrefix;
        }
        File file = new File(tempPrePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(imagePrePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @ApiOperation("上传单个文件")
    @PostMapping("/upload/single")
    @ResponseBody
//    @RateLimiter(value = 10, timeout = 100)
    public Map<String, Object> uploadSingle(HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding(character);
        JSONObject jsonObject = uploadSingleFile(request, false);
        return StatusCode.success(jsonObject);
    }

    @ApiOperation("上传图片")
    @PostMapping("/upload/picture")
    @ResponseBody
//    @RateLimiter(value = 10, timeout = 100)
    public Map<String, Object> uploadPicture(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(character);
        JSONObject jsonObject = uploadSingleFile(request, true);
        return StatusCode.success(jsonObject);
    }

    /**
     * 上传单个文件
     *
     * @param request   请求
     * @param isPicture 是否是图片，如果是图片则需要检验后缀
     * @return
     */
    private JSONObject uploadSingleFile(HttpServletRequest request, Boolean isPicture) {
        JSONObject jsonObject = new JSONObject();
        // 初始化参数
        init();
        String filePath = tempPrePath;
        if (isPicture) {
            filePath = imagePrePath;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        try {
            // 给文件管理工厂设置缓冲区位置以及大小
            factory.setSizeThreshold(Integer.parseInt(this.sizeThreshold));
            factory.setRepository(new File(tempPrePath));
            // 设置upload的单个文件大小、总请求大小
            ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
            servletFileUpload.setFileSizeMax(Long.parseLong(this.fileSizeMax));
            servletFileUpload.setSizeMax(Long.parseLong(this.requestSizeMax));
            // 获取表单
            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            for (FileItem item : fileItems) {
                // 复杂表单域说明上传的是文件，简单表单域说明是其他参数
                if (!item.isFormField()) {
                    String filename = item.getName();
                    File file = new File(filePath, System.currentTimeMillis() + "_" + filename);
                    // 图片验证后缀
                    if (isPicture) {
                        String fileSuffix = filename.substring(filename.lastIndexOf("."));
                        if (!Strings.hasText(fileSuffix) || !IMAGE_SUFFIX_SET.contains(fileSuffix)) {
                            jsonObject.put("msg", "上传失败");
                            jsonObject.put("error", "文件格式不正确");
                            return jsonObject;
                        }
                    }
                    // 文件写入 todo：后续可以使用Hash减少文件重复上传的次数
                    item.write(file);
                    // 如果是图片只需要获取文件名
                    if (isPicture) {
                        jsonObject.put("pictureName", file.getName());
                    } else {
                        jsonObject.put("filename", file.getAbsolutePath());
                    }
                    // 删除临时文件
                    item.delete();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

}
