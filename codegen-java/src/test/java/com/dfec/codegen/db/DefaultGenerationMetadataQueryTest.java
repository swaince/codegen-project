package com.dfec.codegen.db;

import com.dfec.codegen.config.DatabaseConfig;
import com.dfec.codegen.config.JavaGenerationConfig;
import org.junit.jupiter.api.Test;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
class DefaultGenerationMetadataQueryTest {

    @Test
    void query() {
        JavaGenerationConfig config = JavaGenerationConfig.builder()
                .configureDatabase(builder -> {
                    builder
                            .includeView(false)
                            .jdbcUrl("jdbc:mysql://192.168.66.118:3307/fh-system")
                            .username("root")
                            .password("123456");
                }).build();

        DefaultGenerationMetadataQuery query = new DefaultGenerationMetadataQuery(config);

        GenerationMetadata metadata = query.query();

        System.out.println(metadata);
    }
}