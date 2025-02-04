package com.vet.hc.api.shared.application.adapter.out.seeders;

import static com.vet.hc.api.shared.application.adapter.in.util.AnsiShortcuts.fgBrightYellow;

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
 * Seeder configuration for development environment.
 */
@Slf4j
// Temporary production profile added to test the application in production mode
@Profile({ "dev", "prod" })
@Configuration
public class DevDatabaseSeeder {
    @Value("classpath:sql/dev/seeder.sql")
    private Resource scriptSql;

    @Bean(name = "devDataSourceInitializer")
    DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();

        log.info("Initializing dev database with script: {}",
                fgBrightYellow(scriptSql.getFilename()));
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(scriptSql));

        return initializer;
    }
}
