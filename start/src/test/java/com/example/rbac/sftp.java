package com.example.rbac;

import cn.hutool.json.JSONObject;
import com.example.sftp.service.SftpTools;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

@Slf4j
public class sftp {

    @Test
    public void test1() throws IOException {
        JSONObject jsonObject = SftpTools.getLayer("localhost", 2222, "sftpadmin", "1231");
        log.debug("jsonObject={}", jsonObject.toJSONString(2));

        String remoteFilePath = "/sftp/sshd_config";
        String templateDir = "D:\\";
        String localFilePath = templateDir + File.separator + remoteFilePath.substring(remoteFilePath.lastIndexOf("/")+1);
        SftpTools.downFile("localhost", 2222, "sftpadmin", "1231", localFilePath, remoteFilePath);
    }
}
