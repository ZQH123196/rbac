package com.example.rbac.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("page")
public class PageDO {
    String admdivcode;
    String pageUrl;
}
