package com.example.rbac.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zqh
 */
@Data
@TableName("user")
public class UserDO {
    String name;
    String password;
    String admdivcode;
    String localRolePermission;
}
