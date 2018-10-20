package com.pyg.manager.controller;

import ReturnResult.Results;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.FastDFSClient;

import java.io.Serializable;

/**
 * 文件上传Controller
 */

@RestController
public class UploadController implements Serializable {

    @Value("${FILE_SERVER_URL}")
    private String file_server_url;//文件服务器地址


    @RequestMapping("/upload")
    public Results upload(MultipartFile file){
        //取文件拓展名
        String originalFilename = file.getOriginalFilename();
        String ex = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            //2、创建一个 FastDFS 的客户端
            FastDFSClient fastDFSClient =new FastDFSClient("classpath:config/fdfs_client.conf");
            //3、执行上传处理
            String path = fastDFSClient.uploadFile(file.getBytes(), ex);
            String url=file_server_url+path;
            return new Results(true,url);

        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false,"上传失败");
        }
    }


}
