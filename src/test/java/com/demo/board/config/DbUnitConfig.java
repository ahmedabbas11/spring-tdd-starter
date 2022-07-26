package com.demo.board.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

@Configuration
@ActiveProfiles("test")
public class DbUnitConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public DatabaseConfigBean dbUnitDatabaseConfig() {
        DatabaseConfigBean dbConfig = new com.github.springtestdbunit.bean.DatabaseConfigBean();

        dbConfig.setDatatypeFactory(new org.dbunit.ext.h2.H2DataTypeFactory());
        return dbConfig;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection() {
        DatabaseDataSourceConnectionFactoryBean dbConnection = new com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean(dataSource);
        dbConnection.setDatabaseConfig(dbUnitDatabaseConfig());
        return dbConnection;
    }

}
