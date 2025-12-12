package org.seventhgroup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author cloudsoul
 */

@Configuration
//导入MyBatisConfig和JdbcConfig
@Import({MyBatisConfig.class,JdbcConfig.class})
//扫描包内组件，排除特定类
@ComponentScan(
        value = "org.seventhgroup",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SpringMvcConfig.class)
        }
)
//开启事务管理
@EnableTransactionManagement
public class SpringConfig {
    @Bean("transactionManager")
    public DataSourceTransactionManager getDataSourceTxManager(@Autowired DataSource dataSource){
        DataSourceTransactionManager dtm = new DataSourceTransactionManager();
        dtm.setDataSource(dataSource);
        return dtm;
    }
}