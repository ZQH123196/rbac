package com.example.sftp.controller.simple;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.example.sftp.service.GetFileLayer;
import com.example.sftp.utils.SearchTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sftp")
@Slf4j
//@PropertySource({"classpath:sftp.yml"})
//@ConfigurationProperties(prefix= "myconfig.mail")
public class SftpContoller {

    static final String HOST = "localhost";
    static final Integer PORT = 2222;
    static final String UserName = "sftpadmin";
    static final String Password = "1231";


    @PostMapping("getList")
    public JSONObject getList(@RequestBody Map<String, Object> req) throws IOException {

        JSONObject jsonObject = GetFileLayer.getLayer(HOST, PORT, UserName, Password);
        return jsonObject;
    }

//    @CrossOrigin
    @PostMapping("download")
    public void fileDownload(HttpServletResponse res) throws IOException {
        String remoteFilePath = "/sftp/sshd_config";
        String fileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/")+1);
        String templateDir = "D:\\";
        String localFilePath = templateDir + File.separator + fileName;
        // 将 sftp 文件下载到本地临时文件夹
        boolean downstate = GetFileLayer.downFile("localhost", 2222, "sftpadmin", "1231", localFilePath, remoteFilePath);

        if (!downstate) {
            log.error("下载失败，downstate=", downstate);
//            return new HashMap(){{
//                put("code", "1001");
//            }};
        }

        // 强制性下载
        res.setContentType("applcation/octet-stream");
        res.setHeader("Content-Disposition", "attachement;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        res.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        // 从临时文件夹读取文件返回
        File localFile = new File(localFilePath);
        if (!localFile.exists()) {
            log.error("本地临时文件[{}]不存在！", localFile.getAbsolutePath());
        }
        InputStream inputStream = new FileInputStream(localFile);
        // 每次读取 1024 字节
        byte[] data = new byte[1024];
        Integer len = 0;
        while ( (len = inputStream.read(data)) != -1) {
            res.getOutputStream().write(data, 0, len);
        }
        inputStream.close();




//        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localFile), 8192);) {
//            byte[] data = new byte[8192];
//            Integer len = 0;
//            while ( (len = bis.read(data)) != -1) {
//                res.getOutputStream().write(data, 0, len);
//            }
//        }


//        return new HashMap(){{
//            put("code", "200");
//        }};
    }

    // 下载本地文件
//    @CrossOrigin
//    @PostMapping("download")
//    public void fileDownload(HttpServletResponse res) throws IOException {
//        String fileName = "asd.txt";
//        // 强制性下载
//        res.setContentType("application/force-download");
//        res.setHeader("Content-Disposition", "attachement;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//        res.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//
//        Resource fileResource = new ClassPathResource("/sftp.yml");
//        // ID 流读取文件内容，然后一点点进行文件下载
//        InputStream inputStream = fileResource.getInputStream();
//        // 每次读取 1024 字节
//        byte[] data = new byte[1024];
//        Integer len = 0;
//        while ( (len = inputStream.read(data)) != -1) {
//            res.getOutputStream().write(data, 0, len);
//        }
//    }
}
