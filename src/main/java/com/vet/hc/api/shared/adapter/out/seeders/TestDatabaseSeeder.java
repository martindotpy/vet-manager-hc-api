package com.vet.hc.api.shared.adapter.out.seeders;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightYellow;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import lombok.extern.slf4j.Slf4j;

/**
 * Seeder configuration for test environment.
 */
@Slf4j
@Profile("test")
@Configuration
public class TestDatabaseSeeder {
    @Value("classpath:sql/test/seeder.sql")
    private Resource scriptSql;

    @Bean(name = "testDataSourceInitializer")
    DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();

        log.info("Initializing test database with script: {}",
                fgBrightYellow(scriptSql));

        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(scriptSql));

        return initializer;
    }
}
