package com.jdbc.demo.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class SpringJdbcConfig {

//	    @Bean
//	    public DataSource dataSource() {
//	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	        dataSource.setDriverClassName("org.postgresql.Driver");
//	        dataSource.setUrl("jdbc:postgresql://localhost:5432/database-test");
//	        dataSource.setUsername("postgres");
//	        dataSource.setPassword("pgAdmin");
//	        return dataSource;
//	    }
//
//	    @Bean
//	    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//	        return new JdbcTemplate(dataSource);
//	    }
	}

