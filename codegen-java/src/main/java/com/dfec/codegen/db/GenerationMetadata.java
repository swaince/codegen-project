package com.dfec.codegen.db;

import com.dfec.codegen.config.JavaGenerationConfig;
import lombok.Data;

import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.Set;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
@Data
public class GenerationMetadata {

    private DatabaseMetaData metadata;

    private Set<String> keywords;

    private String identifierQuoteString;

    private List<Table> tables;

    private JavaGenerationConfig config;
}
