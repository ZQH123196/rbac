package com.example.rbac.controller.restful;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.dao.AdmdivMapper;
import com.example.rbac.dao.model.AdmdivDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Rbac/restful/role")
public class AdmdivController {

    @Autowired
    private AdmdivMapper roleMapper;


    /**
     * SELECT 查询操作，返回一个 JSON 数组
     * 幂等
     * */
    @GetMapping("/role")
    @ResponseStatus(HttpStatus.OK)
    public Object getAdmdivs() {
        List<AdmdivDO> roleDOList = roleMapper.selectList(null);

        return roleDOList;
    }

    /**
     * SELECT 查询操作，返回一个 JSON 对象
     * 幂等
     * */
    @GetMapping("/role/{role}/{admdivcode}")
    @ResponseStatus(HttpStatus.OK)
    public Object getAdmdiv(
            @PathVariable("role") String role,
            @PathVariable("admdivcode") String admdivcode
    ) {
        Map<String, String> queryMap = new HashMap<String, String>(16){{
            put("role", role);
            put("admdivcode", admdivcode);
        }};

        AdmdivDO _role = roleMapper.selectOne(new QueryWrapper<AdmdivDO>().allEq(queryMap));
        return _role;
    }


    /**
     * 新增一个用户对象
     * 非幂等
     * */
    @PostMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addAdmdiv(@RequestBody AdmdivDO role){
        int insertNum = roleMapper.insert(role);
        return insertNum;
    }

    /**
     * 编辑一个用户对象
     * 幂等性
     * */
    @PutMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    public Object editAdmdiv(@RequestBody AdmdivDO role) {
        int updateNum = roleMapper.updateById(role);
        return updateNum;
    }

    /**
     * 删除一个用户对象
     * 幂等性
     * */
    @DeleteMapping("/role/{admdivcode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Object deleteAdmdiv(@PathVariable("admdivcode") String admdivcode){
        int deleteNum = roleMapper.deleteById(admdivcode);
        return  deleteNum;
    }


}
