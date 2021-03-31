package com.example.rbac.controller.restful;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.dao.PageMapper;
import com.example.rbac.dao.model.PageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zqh
 */
@RestController
@RequestMapping("/Rbac/restful/page")
public class PageController {

    @Autowired
    private PageMapper pageMapper;


    /**
     * SELECT 查询操作，返回一个 JSON 数组
     * 幂等
     * */
    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Object getPages() {
        List<PageDO> pageDOList = pageMapper.selectList(null);

        return pageDOList;
    }

    /**
     * SELECT 查询操作，返回一个 JSON 对象
     * 幂等
     * */
    @GetMapping("/page/{name}/{admdivcode}")
    @ResponseStatus(HttpStatus.OK)
    public Object getPage(
            @PathVariable("name") String name,
            @PathVariable("admdivcode") String admdivcode
    ) {
        Map<String, String> queryMap = new HashMap<String, String>(16){{
            put("name", name);
            put("admdivcode", admdivcode);
        }};

        PageDO page = pageMapper.selectOne(new QueryWrapper<PageDO>().allEq(queryMap));
        return page;
    }


    /**
     * 新增一个用户对象
     * 非幂等
     * */
    @PostMapping("/page")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addPage(@RequestBody PageDO page){

        int insertNum = pageMapper.insert(page);

        return insertNum;
    }

    /**
     * 编辑一个用户对象
     * 幂等性
     * */
    @PutMapping("/page/{pageUrl}/{admdivcode}")
    @ResponseStatus(HttpStatus.CREATED)
    public Object editPage(@PathVariable("pageUrl") String pageUrl,
                           @PathVariable("admdivcode") String admdivcode,
                           @RequestBody PageDO page) {

        Map updateMap = new HashMap(16){{
            put("pageUrl", pageUrl);
            put("admdivcode", admdivcode);
        }};

        int updateNum = pageMapper.update(page, new QueryWrapper<PageDO>().allEq(updateMap));

        return updateNum;
    }

    /**
     * 删除一个用户对象
     * 幂等性
     * */
    @DeleteMapping("/page/{pageUrl}/{admdivcode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Object deletePage(@PathVariable("pageUrl") String pageUrl,
                             @PathVariable("admdivcode") String admdivcode
    ){
        Map deleteMap = new HashMap(16){{
            put("pageUrl", pageUrl);
            put("admdivcode", admdivcode);
        }};
        int deleteNum = pageMapper.delete(new QueryWrapper<>().allEq(deleteMap));
        return  deleteNum;
    }


}
