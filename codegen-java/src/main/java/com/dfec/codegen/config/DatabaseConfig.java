package com.dfec.codegen.config;

import com.dfec.codegen.db.GenerationMetadataQuery;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
@Data
@Builder(builderClassName = "Builder")
public class DatabaseConfig {

    private String jdbcUrl;
    private String username;
    private String password;

    @Default
    private boolean includeView = false;

    private GenerationMetadataQuery metadataQuery;
}
