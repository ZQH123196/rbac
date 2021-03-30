package com.example.demoFrontEnd;

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
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<UserDO> userList = userMapper.selectList(null);

        Assert.assertEquals(true, userList.size() > 0);
        userList.forEach(System.out::println);
    }
}


