package com.example.rbac.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zqh
 */
@Data
@TableName("page")
public class PageDO {
    String admdivcode;
    String pageUrl;
}
