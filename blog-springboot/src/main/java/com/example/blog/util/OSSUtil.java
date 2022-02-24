package com.example.blog.util;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

public class OSSUtil {
    private static String url;

    private static String endpoint;

    private static String accessKeyId;

    private static String accessKeySecret;

    private static String bucketName;

    @Value("${upload.oss.url}")
    public void setUrl(String url) {
        OSSUtil.url = url;
    }

    @Value("${upload.oss.endpoint}")
    public void setEndpoint(String endpoint) {
        OSSUtil.endpoint = endpoint;
    }

    @Value("${upload.oss.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        OSSUtil.accessKeyId = accessKeyId;
    }

    @Value("${upload.oss.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        OSSUtil.accessKeySecret = accessKeySecret;
    }

    @Value("${upload.oss.bucketName}")
    public void setBucketName(String bucketName) {
        OSSUtil.bucketName = bucketName;
    }

    /**
     * 上传图片
     *
     * @param file       文件
     * @param targetAddr 目标路径
     * @return
     */
    public static String upload(MultipartFile file, String targetAddr) {
        // 获取不重复的随机名
        String fileName = String.valueOf(IdWorker.getId());
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + fileName + extension;
        try {
            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件
            ossClient.putObject(bucketName, relativeAddr, file.getInputStream());
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url + relativeAddr;
    }

    /**
     * 获取输入文件流的扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
