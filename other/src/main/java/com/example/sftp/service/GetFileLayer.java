package com.example.sftp.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.sftp.utils.SearchTool;
import com.sun.xml.internal.ws.wsdl.writer.document.Port;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.DisconnectReason;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.UserAuthException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class GetFileLayer {

    public static void main(String[] args) throws IOException {
//        JSONObject jsonObject = getLayer("localhost", 2222, "sftpadmin", "1231");
//        log.debug("jsonObject={}", jsonObject.toJSONString(2));


        String remoteFilePath = "/sftp/sshd_config";
        String templateDir = "D:\\";
        String localFilePath = templateDir + File.separator + remoteFilePath.substring(remoteFilePath.lastIndexOf("/")+1);
        downFile("localhost", 2222, "sftpadmin", "1231", localFilePath, remoteFilePath);

    }



    // 覆盖本地文件
    public static boolean downFile(String HOST, Integer PORT, String UserName, String Password, String localFilePath, String remoteFilePath) throws IOException {
        SSHClient ssh = new SSHClient();
        ssh.loadKnownHosts();
        try {
            ssh.connect(HOST, PORT);
        } catch (TransportException e) {
            // 报 [HOST_KEY_NOT_VERIFIABLE] Could not verify `ssh-ed25519` host key with fingerprint 就将指纹加入在连接。
            if (e.getDisconnectReason() == DisconnectReason.HOST_KEY_NOT_VERIFIABLE) {
                String msg = e.getMessage();
                String[] split = msg.split("`");
                // 分割出 key
                String vc = split[3];
                ssh = new SSHClient();
                // 添加该地址的指纹
                ssh.addHostKeyVerifier(vc);
                ssh.connect(HOST, PORT);
            } else {
                throw e;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ssh.authPassword(UserName, Password);
        try {
            SFTPClient sftp = ssh.newSFTPClient();
            try {
                // 普通文件 REGULAR
                // 文件夹 DIRECTORY
                sftp.get(remoteFilePath, localFilePath);
            } finally {
                sftp.close();
            }
        } finally {
            ssh.disconnect();
        }
        return false;
    }

    public static JSONObject getLayer(String HOST, Integer PORT, String UserName, String Password) throws IOException {
        SSHClient ssh = new SSHClient();
        ssh.loadKnownHosts();
        try {
            ssh.connect(HOST, PORT);
        } catch (TransportException e) {
            // 报 [HOST_KEY_NOT_VERIFIABLE] Could not verify `ssh-ed25519` host key with fingerprint 就将指纹加入在连接。
            if (e.getDisconnectReason() == DisconnectReason.HOST_KEY_NOT_VERIFIABLE) {
                String msg = e.getMessage();
                String[] split = msg.split("`");
                // 分割出 key
                String vc = split[3];
                ssh = new SSHClient();
                // 添加该地址的指纹
                ssh.addHostKeyVerifier(vc);
                ssh.connect(HOST, PORT);
            } else {
                throw e;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = null;
        ssh.authPassword(UserName, Password);
        try {
            SFTPClient sftp = ssh.newSFTPClient();
            try {
                // 普通文件 REGULAR
                // 文件夹 DIRECTORY
                List<RemoteResourceInfo> remoteResourceInfos = sftp.ls("/sftp/");
                jsonObject = JSONUtil.parseObj(SearchTool.deepSearch(sftp, remoteResourceInfos, "/sftp"));

            } finally {
                sftp.close();
            }
        } finally {
            ssh.disconnect();
        }
        return jsonObject;
    }
}
