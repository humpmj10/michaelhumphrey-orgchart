package com.accenture.javadojo.michael.humphrey_orgchart.config;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

public class DatabaseConfig {

    @Inject
    private DataSource dataSource;

    public DatabaseConfig() {

    }

    public void initDataBase() {

        try {
            ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("insert.sql"));
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters/Setters
    public DataSource getDataSource() {

        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
    }
}
