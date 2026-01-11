package com.dfec.codegen.db;

import com.dfec.codegen.config.JavaGenerationConfig;
import lombok.Data;

import java.sql.DatabaseMetaData;
import java.util.List;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
@Data
public class GenerationMetadata {

    DatabaseMetaData metadata;

    List<Table> tables;

    JavaGenerationConfig config;
}
