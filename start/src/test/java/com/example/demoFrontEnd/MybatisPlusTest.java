package com.example.demoFrontEnd;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.example.demoFrontEnd.dao.UserMapper;
import com.example.demoFrontEnd.dao.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MybatisPlusTest {
    @Resource
    private UserMapper userMapper;


    @Test
    public void testUserCRUD() {
        // insert
        int insertNum = userMapper.insert(
                new UserDO(){{
                    setAdmdivcode("10086");
                    setName("移动");
                    setPassword("1008610086");
                    setLocalRolePermission("admin");
                }}
        );
        log.info("insertNum={}", insertNum);

        // select
        List<UserDO> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);

        // update
        int updateNum = userMapper.update(
                new UserDO() {{
                    setLocalRolePermission("a piese of shit!");
                }},
                new QueryWrapper<UserDO>().eq("name", "移动")
        );
        log.info("updateNum={}", updateNum);

        // delect
        int deleteNum = userMapper.delete(new QueryWrapper<UserDO>().eq("name", "移动"));
        log.info("deleteNum={}", deleteNum);
    }


    @Test
    public void testQueryUserByMap() {
        Map<String, Object> queryMap = new HashMap<String, Object>(){{
            put("name", "eor");
            put("admdivcode", "440900");
        }};

//        UserDO user = userMapper.selectOne(new QueryWrapper<UserDO>().allEq(queryMap));
        List<UserDO> user =  userMapper.selectByMap(queryMap);

        log.info("user=[{}]", user);
    }

}


