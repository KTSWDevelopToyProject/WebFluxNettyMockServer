package com.kt.iotmakers.webfluxnettymockserver.config.database;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class R2DbcPostgreSqlConfig {

    @Bean
    public PostgresqlConnectionFactory getPostgresqlConnectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("localhost")
                        .username("donghyeon")
                        .port(5432)
                        .build()
        );
    }
}
