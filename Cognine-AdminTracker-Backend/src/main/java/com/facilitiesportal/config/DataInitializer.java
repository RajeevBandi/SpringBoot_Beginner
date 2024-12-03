package com.facilitiesportal.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
	
	private final DataSource dataSource;

	private final Resource dataScript;

	public DataInitializer(DataSource dataSource, @Value("classpath:/db_scripts.sql") Resource dataScript) {
		this.dataSource = dataSource;
		this.dataScript = dataScript;
	}

	@EventListener
	public void onApplicationReady(ContextRefreshedEvent event) {
		try (Connection connection = dataSource.getConnection()) {
			ScriptUtils.executeSqlScript(connection, dataScript);
		} catch ( SQLException ex) {
			System.out.println(ex);
		}
	}

}
