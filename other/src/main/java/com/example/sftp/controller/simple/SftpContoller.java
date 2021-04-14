package com.example.sftp.controller.simple;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.example.sftp.service.GetFileLayer;
import com.example.sftp.utils.SearchTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/sftp")
@Slf4j
//@PropertySource({"classpath:sftp.yml"})
//@ConfigurationProperties(prefix= "myconfig.mail")
public class SftpContoller {

    static final String HOST = "localhost";
    static final Integer PORT = 2222;
    static final String username = "sftpadmin";
    static final String password = "1231";


    @PostMapping("/getList")
    public JSONObject getList(@RequestBody Map<String, Object> req) throws IOException {

        JSONObject jsonObject = GetFileLayer.getLayer(HOST, PORT, username, password);



        return jsonObject;
    }

}
