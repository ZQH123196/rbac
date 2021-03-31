package com.example.rbac.controller.restful;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.dao.UserMapper;
import com.example.rbac.dao.model.UserDO;
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
@RequestMapping("/Rbac/restful/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;


    /**
     * SELECT 查询操作，返回一个 JSON 数组
     * 幂等
     * */
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public Object getUsers() {
        List<UserDO> userDOList = userMapper.selectList(null);

        return userDOList;
    }

    /**
     * SELECT 查询操作，返回一个 JSON 对象
     * 幂等
     * */
    @GetMapping("/user/{name}/{admdivcode}")
    @ResponseStatus(HttpStatus.OK)
    public Object getUser(
            @PathVariable("name") String name,
            @PathVariable("admdivcode") String admdivcode
    ) {
        Map<String, String> queryMap = new HashMap<String, String>(16){{
            put("name", name);
            put("admdivcode", admdivcode);
        }};

        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().allEq(queryMap));
        return user;
    }


    /**
     * 新增一个用户对象
     * 非幂等
     * */
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addUser(@RequestBody UserDO user){

        int insertNum = userMapper.insert(user);

        return insertNum;
    }

    /**
     * 编辑一个用户对象
     * 幂等性
     * */
    @PutMapping("/user/{name}/{admdivcode}")
    @ResponseStatus(HttpStatus.CREATED)
    public Object editUser(@PathVariable("name") String name,
                           @PathVariable("admdivcode") String admdivcode,
                           @RequestBody UserDO user) {

        Map updateMap = new HashMap(16){{
            put("name", user.getName());
            put("admdivcode", user.getAdmdivcode());
        }};

        int updateNum = userMapper.update(user, new QueryWrapper<UserDO>().allEq(updateMap));


        return updateNum;
    }

    /**
     * 删除一个用户对象
     * 幂等性
     * */
    @DeleteMapping("/user/{name}/{admdivcode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Object deleteUser(@PathVariable("name") String name,
                             @PathVariable("admdivcode") String admdivcode
                             ){
        Map deleteMap = new HashMap(16){{
            put("name", name);
            put("admdivcode", admdivcode);
        }};
        int deleteNum = userMapper.delete(new QueryWrapper<>().allEq(deleteMap));
        return  deleteNum;
    }


}
