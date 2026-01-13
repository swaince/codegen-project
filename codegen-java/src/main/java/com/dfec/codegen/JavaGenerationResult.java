package com.dfec.codegen;

import com.dfec.codegen.db.GenerationMetadata;
import lombok.Data;

import java.util.Map;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
@Data
public class JavaGenerationResult implements GenerationResult {

    private GenerationMetadata metadata;

    private Map<String, JavaGenerationModel> models;
}
