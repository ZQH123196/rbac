package com.example.rbac.controller.restful;

import com.example.rbac.dao.model.UserDOTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * RESTful API 风格示例 对资源 user 进行操作
 * 本示例没有使用数据库，也没有使用 service 类来辅助完成，所有操作在本类中完成
 *
 * @author zqh*/
@RestController
@RequestMapping("/api/user")
public class TestUserController {



    /**
     * 模拟一组数据
     * */
    private List<UserDOTest> getData(){
        List<UserDOTest> list=new ArrayList<>();

        UserDOTest userDO=new UserDOTest();
        userDO.setUserId(1);
        userDO.setUserName("admin");
        list.add(userDO);

        userDO=new UserDOTest();
        userDO.setUserId(2);
        userDO.setUserName("heike");
        list.add(userDO);

        userDO=new UserDOTest();
        userDO.setUserId(3);
        userDO.setUserName("tom");
        list.add(userDO);

        userDO=new UserDOTest();
        userDO.setUserId(4);
        userDO.setUserName("mac");
        list.add(userDO);

        return  list;
    }


    /**
     * SELECT 查询操作，返回一个JSON数组
     * 具有幂等性
     * */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Object getUsers(){
        List<UserDOTest> list=new ArrayList<>();

        list=getData();

        return list;
    }

    /**
     * SELECT 查询操作，返回一个新建的JSON对象
     * 具有幂等性
     * */
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getUser(@PathVariable("id") String id){

        if(null==id){
            return  null;
        }

        List<UserDOTest> list= getData();
        UserDOTest userDO=null;
        for (UserDOTest user:list
        ) {
            if(id.equals(user.getUserId().toString())){
                userDO=user;
                break;
            }
        }

        return userDO;
    }

    /**
     * 新增一个用户对象
     * 非幂等
     * */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addUser(@RequestBody UserDOTest user){

        List<UserDOTest> list= getData();
        list.add(user);//模拟向列表中增加数据
        return user;
    }

    /**
     * 编辑一个用户对象
     * 幂等性
     * */
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Object editUser(@PathVariable("id") String id,@RequestBody UserDOTest user){
        List<UserDOTest> list= getData();
        for (UserDOTest userDO1:list) {
            if(id.equals(userDO1.getUserId().toString())){
                userDO1=user;
                break;
            }
        }

        return user;
    }

    /**
     * 删除一个用户对象
     * 幂等性
     * */
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Object deleteUser(@PathVariable("id") String id){
        List<UserDOTest> list= getData();
        UserDOTest userDO=null;
        for (UserDOTest user:list
        ) {
            if(id.equals(user.getUserId().toString())){
                //删除用户
                userDO=user;
                break;
            }
        }
        return  userDO;//返回被删除的对象
    }
}