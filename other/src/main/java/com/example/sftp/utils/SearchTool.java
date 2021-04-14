package com.example.sftp.utils;

import net.schmizz.sshj.sftp.FileAttributes;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTool {
    public static Map<String, Object> deepSearch(SFTPClient sftp, List<RemoteResourceInfo> nameList, String startPath) throws IOException {

        Map<String, Object> _nodeTree = new HashMap<>();
        for (int i = 0; i < nameList.size(); i++) {
            FileAttributes arrtributes = nameList.get(i).getAttributes();
            if (arrtributes.getType().toString() == "DIRECTORY") {
                String currentDirPath = startPath + "/" + nameList.get(i).getName();
                List<RemoteResourceInfo> childTreeList = sftp.ls(currentDirPath);
                _nodeTree.put(nameList.get(i).getName(), SearchTool.deepSearch(sftp, childTreeList, currentDirPath));
            } else { // 非文件夹存入 String
                String key = nameList.get(i).getName();
                String value = nameList.get(i).getName();
                _nodeTree.put(key, value);
            }
        }
        return _nodeTree;
    }

}
