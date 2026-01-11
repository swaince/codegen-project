package com.dfec.codegen.db;

import com.dfec.codegen.config.DatabaseConfig;
import org.junit.jupiter.api.Test;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
class DefaultDatabaseQueryTest {

    @Test
    void getTable() {
        DatabaseConfig config = DatabaseConfig
                .builder()
                .includeView(false)
                .jdbcUrl("jdbc:mysql://192.168.66.118:3307/fh-system")
                .username("root")
                .password("123456")
                .build();
    }
}