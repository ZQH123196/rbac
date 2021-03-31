package com.example.rbac.controller.restful;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.dao.RoleMapper;
import com.example.rbac.dao.model.RoleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Rbac/restful/role")
public class RoleController {

    @Autowired
    private RoleMapper roleMapper;


    /**
     * SELECT 查询操作，返回一个 JSON 数组
     * 幂等
     * */
    @GetMapping("/role")
    @ResponseStatus(HttpStatus.OK)
    public Object getRoles() {
        List<RoleDO> roleDOList = roleMapper.selectList(null);

        return roleDOList;
    }

    /**
     * SELECT 查询操作，返回一个 JSON 对象
     * 幂等
     * */
    @GetMapping("/role/{role}/{admdivcode}")
    @ResponseStatus(HttpStatus.OK)
    public Object getRole(
            @PathVariable("role") String role,
            @PathVariable("admdivcode") String admdivcode
    ) {
        Map<String, String> queryMap = new HashMap<String, String>(16){{
            put("role", role);
            put("admdivcode", admdivcode);
        }};

        RoleDO _role = roleMapper.selectOne(new QueryWrapper<RoleDO>().allEq(queryMap));
        return _role;
    }


    /**
     * 新增一个用户对象
     * 非幂等
     * */
    @PostMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addRole(@RequestBody RoleDO role){

        int insertNum = roleMapper.insert(role);

        return insertNum;
    }

    /**
     * 编辑一个用户对象
     * 幂等性
     * */
    @PutMapping("/role/{role}/{admdivcode}")
    @ResponseStatus(HttpStatus.CREATED)
    public Object editRole(@PathVariable("role") String roleName,
                           @PathVariable("admdivcode") String admdivcode,
                           @RequestBody RoleDO role) {

        Map updateMap = new HashMap(16){{
            put("role", roleName);
            put("admdivcode", admdivcode);
        }};

        int updateNum = roleMapper.update(role, new QueryWrapper<RoleDO>().allEq(updateMap));


        return updateNum;
    }

    /**
     * 删除一个用户对象
     * 幂等性
     * */
    @DeleteMapping("/role/{role}/{admdivcode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Object deleteRole(@PathVariable("role") String role,
                             @PathVariable("admdivcode") String admdivcode
    ){
        Map deleteMap = new HashMap(16){{
            put("name", role);
            put("admdivcode", admdivcode);
        }};
        int deleteNum = roleMapper.delete(new QueryWrapper<>().allEq(deleteMap));
        return  deleteNum;
    }


}
