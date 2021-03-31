package com.example.rbac.dao.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zqh
 */
@Data
@TableName("admdiv")
public class AdmdivDO {
    @TableId
    String admdivcode;
    String page_permission;
    String role_permission;
}




