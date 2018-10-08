package com.readin.robot.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


@Configuration
//加上这个注解，使得支持事务
@EnableTransactionManagement
public class MybatisConfig implements TransactionManagementConfigurer {
    @Autowired
    private DataSource dataSource;
        
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

   @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean sn = new SqlSessionFactoryBean();
        sn.setDataSource(dataSource);
        //bean.setTypeAliasesPackage("com.readin.robot.mapping");

        /*//分页插件
        PaginatePlugin paginateUtil = new PaginatePlugin();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        paginateUtil.setProperties(properties);

        //添加插件
        sn.setPlugins(new Interceptor[]{paginateUtil});*/

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        
        try {
        	sn.setMapperLocations(resolver.getResources("classpath:mapping/*.xml"));
        	//sn.setConfigLocation(resolver.getResource("classpath:mybatis.xml"));
            return sn.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    	
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}