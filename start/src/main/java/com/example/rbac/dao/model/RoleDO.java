package com.example.rbac.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role")
public class RoleDO {
    String role;
    String admdivcode;
    String pages;
}
