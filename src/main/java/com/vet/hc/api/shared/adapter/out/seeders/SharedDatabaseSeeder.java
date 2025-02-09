package com.vet.hc.api.shared.adapter.out.seeders;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightYellow;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import lombok.extern.slf4j.Slf4j;

/**
 * Seeder configuration for all environment.
 */
@Slf4j
@Configuration
public class SharedDatabaseSeeder {
    @Value("classpath:sql/shared/seeder.sql")
    private Resource scriptSql;

    @Bean(name = "sharedDataSourceInitializer")
    DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();

        log.info("Initializing shared database with script: {}",
                fgBrightYellow(scriptSql));

        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(scriptSql));

        return initializer;
    }
}
