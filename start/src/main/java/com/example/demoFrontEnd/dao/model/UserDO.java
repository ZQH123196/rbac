package com.example.demoFrontEnd.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserDO {
    @TableId
    String name;
    String password;
    String admdivcode;
    String localRolePermission;
}
