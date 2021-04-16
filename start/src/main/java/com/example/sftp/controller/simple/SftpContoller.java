package com.example.sftp.controller.simple;


import cn.hutool.json.JSONObject;
import com.example.sftp.service.SftpTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

@RestController
@RequestMapping("/sftp")
@Slf4j
//@PropertySource({"classpath:sftp.yml"})
//@ConfigurationProperties(prefix = "sftp")
public class SftpContoller {

    @Value("${sftp.host}")
    String host;
    @Value("${sftp.port}")
    Integer port;
    @Value("${sftp.username}")
    String username;
    @Value("${sftp.password}")
    String password;
    @Value("${sftp.templateDir}")
    String templateDir;


    @CrossOrigin
    @PostMapping("getList")
    public JSONObject getList() throws IOException {
        JSONObject jsonObject = SftpTools.getLayer(host, port, username, password);
        return jsonObject;
    }

    @CrossOrigin
    @PostMapping("download")
    public void fileDownload(@RequestBody Map<String, Object> req, HttpServletResponse res) throws IOException {

        String remoteFilePath = (String) req.get("downFilePath");
        String fileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/")+1);
        String localFilePath = templateDir + File.separator + fileName;
        // 将 sftp 文件下载到本地临时文件夹
        SftpTools.downFile(host, port, username, password, localFilePath, remoteFilePath);

        // 强制性下载
        res.setContentType("applcation/octet-stream");
        res.setHeader("Content-Disposition", "attachement;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        res.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        // 从临时文件夹读取文件返回
        File localFile = new File(localFilePath);
        if (!localFile.exists()) {
            log.error("本地临时文件[{}]不存在！", localFile.getAbsolutePath());
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localFile), 8192);) {
            byte[] data = new byte[8192];
            Integer len = 0;
            while ( (len = bis.read(data)) != -1) {
                res.getOutputStream().write(data, 0, len);
            }
        }

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
