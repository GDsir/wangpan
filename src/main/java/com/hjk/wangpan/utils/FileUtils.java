package com.hjk.wangpan.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileUtils {

    /**
     * 将文件移动到指定路径
     *
     * @param originalFile
     * @param targetFile
     * @return
     */
    public static boolean moveFile(File originalFile, File targetFile) {
        if (!originalFile.exists()) {
            return false;
        }
        targetFile.mkdirs();
        Path source = originalFile.toPath();
        Path destination = targetFile.toPath();
        try {
            Files.walk(source).parallel().forEach(path -> {
                Path target = destination.resolve(source.relativize(path));
                try {
                    Files.move(path, target, REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException("文件移动失败，文件IO失败");
                }
            });
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
