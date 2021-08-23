package com.example.Phase12;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;



public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

    @Autowired
    DataSource dataSource;

    @Override
    protected DataSource getDataSource() {

        return DataSourceBuilder
                .create()
                .build();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return null;
    }
}
