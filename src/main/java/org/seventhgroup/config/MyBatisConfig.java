package org.seventhgroup.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author cloudsoul
 */
@Configuration
public class MyBatisConfig {

    //配置PageInterceptor分页插件
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        // 启用分页功能
        properties.setProperty("value", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    //配置SQL会话工厂Bean
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(
            @Autowired DataSource dataSource,
            @Autowired PageInterceptor pageInterceptor) throws IOException {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        //设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);

        //配置分页插件
        Interceptor[] plugins = { pageInterceptor };
        sqlSessionFactoryBean.setPlugins(plugins);

        //设置XML映射文件路径（扫描resources/mapper目录下的所有XML文件）
        Resource[] mapperResources =  new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(mapperResources);

        //设置类型别名包
        sqlSessionFactoryBean.setTypeAliasesPackage("org.seventhgroup.pojo,org.seventhgroup.dto");

        return sqlSessionFactoryBean;
    }

    //配置Mapper接口扫描器
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        //设置Mapper接口所在的包路径
        msc.setBasePackage("org.seventhgroup.dao");
        return msc;
    }
}