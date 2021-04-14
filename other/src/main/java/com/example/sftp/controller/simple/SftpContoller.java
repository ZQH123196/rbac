package com.example.sftp.controller.simple;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.example.sftp.service.GetFileLayer;
import com.example.sftp.utils.SearchTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
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

    @CrossOrigin
    @PostMapping("download")
    public void fileDownload(HttpServletResponse res) throws IOException {
        String filename = "downByBackend";
        // 强制性下载
        res.setContentType("applcation/octet-stream");
        res.setHeader("Content-Disposition", "attachement;filename=" + URLEncoder.encode(filename, "UTF-8"));
        res.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        // 将 sftp 文件下载到本地临时文件夹

        // 从临时文件夹读取文件返回
        Resource fileResource = new ClassPathResource("/sftp.yml");
        // ID 流读取文件内容，然后一点点进行文件下载
        InputStream inputStream = fileResource.getInputStream();
        // 每次读取 1024 字节
        byte[] data = new byte[1024];
        Integer len = 0;
        while ( (len = inputStream.read(data)) != -1) {
            res.getOutputStream().write(data, 0, len);
        }
    }

    // 下载本地文件
//    @GetMapping("downloadLocal")
//    public void fileDownload(HttpServletResponse res) throws IOException {
//        String filename = "ScreenOff .bat";
//        // 强制性下载
//        res.setContentType("applcation/force-download");
//        res.setHeader("Content-Disposition", "attachement;filename=" + filename);
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
