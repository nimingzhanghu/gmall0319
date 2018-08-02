package com.atguigu.gmall.manage.controller;


import org.apache.commons.lang3.StringUtils;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 图片上传控制器 --> 相当于拼接了一个图片的存储地址
 */
@RestController
public class FileUploadController {

    //@Value : 可以从application.properties配置文件中取得数据
    //此时的fileUrl就是注解从application.properties中取得的值 192.168.91.130
    @Value("${fileServer.url}")
    String fileUrl;


    @RequestMapping(value = "fileUpload",method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException, MyException {

        String imgUrl=fileUrl;


        if(file!=null){

            System.out.println("multipartFile = " + file.getName()+"|"+file.getSize());

            //初始化工作
            String configFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(configFile);
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getConnection();
            StorageClient storageClient=new StorageClient(trackerServer,null);

            //取得上传文件名称
            String filename = file.getOriginalFilename();
            //取得后缀名称
            String extName = StringUtils.substringAfterLast(filename, ".");

            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);


            imgUrl=fileUrl ;

            for (int i = 0; i < upload_file.length; i++) {

                String path = upload_file[i];

                imgUrl+="/"+path;
            }

        }

        return imgUrl;
    }

}

