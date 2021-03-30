package com.example.demoFrontEnd.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;



import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.example.demoFrontEnd.**.dao"},
        sqlSessionFactoryRef = "ds1-SqlSessionFactory")
public class Ds1DataSourceConfig {

    @Profile({"dev", "prod"})
    @Bean(name = {"ds1", "datasource1"})
    @ConfigurationProperties("spring.datasource.druid.ds1")
    public DataSource dataSourceJdbc() {
        return DruidDataSourceBuilder.create().build();
    }


    @Primary
    @Bean(name = {"ds1-SqlSessionFactory"})
    public SqlSessionFactory sqlSessionFactory(@Qualifier("ds1")DataSource dataSource) throws Exception {
        // MP 全局设置
        GlobalConfig globalConfig = new GlobalConfig();


        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);


        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);

        return mybatisSqlSessionFactoryBean.getObject();
    }


}
