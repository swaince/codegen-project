package com.dfec.codegen.resolver;

import com.dfec.codegen.JavaGenerationModel;
import com.dfec.codegen.db.GenerationMetadata;
import com.dfec.codegen.db.Table;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public interface ModelResolver<T> {

    T resolve(GenerationMetadata metadata, Table table);
}
