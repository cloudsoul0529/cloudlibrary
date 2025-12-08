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

@Configuration
public class MyBatisConfig {

    /**配置PageInterceptor分页插件*/
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("value", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Autowired DataSource dataSource,
                                                          @Autowired PageInterceptor pageInterceptor) throws IOException {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();

        //设置数据源
        ssfb.setDataSource(dataSource);

        //设置分页插件
        Interceptor[] plugins = {pageInterceptor};
        ssfb.setPlugins(plugins);

        //设置XML映射文件路径（resources目录下）
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml");
        ssfb.setMapperLocations(resources);
        //给包设置别名
        ssfb.setTypeAliasesPackage("org.seventhgroup.pojo, org.seventhgroup.dto");
        return ssfb;
    }

    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("org.seventhgroup.dao");
        return msc;
    }
}
