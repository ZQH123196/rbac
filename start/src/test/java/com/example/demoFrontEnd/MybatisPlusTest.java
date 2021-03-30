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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MybatisPlusTest {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testUserSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<UserDO> userList = userMapper.selectList(null);

        Assert.assertEquals(true, userList.size() > 0);
        userList.forEach(System.out::println);
    }

    @Test
    public void testUserInsert() {
        int updateNum = userMapper.insert(
                new UserDO(){{
                    setAdmdivcode("10086");
                    setName("移动");
                    setPassword("1008610086");
                    setLocalRolePermission("admin");
                }}
        );

        Assert.assertTrue(updateNum == 1);
        log.info("updateNum={}", updateNum);
    }

    @Test
    public void testUserDelect() {
        int deleteNum = userMapper.delete(new QueryWrapper<UserDO>().eq("name", "移动"));

        log.info("deleteNum={}", deleteNum);
    }

    @Test
    public void testUserUpdate() {
        int updateNum = userMapper.update(
                new UserDO() {{
                    setLocalRolePermission("a piese of shit!");
                }},
                new QueryWrapper<UserDO>().eq("name", "移动")
        );

        log.info("updateNum={}", updateNum);
    }
}


