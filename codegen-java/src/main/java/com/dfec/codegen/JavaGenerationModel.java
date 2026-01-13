package com.dfec.codegen;

import com.dfec.codegen.db.GenerationMetadata;
import com.dfec.codegen.db.Table;
import com.dfec.codegen.model.EntityModel;
import com.dfec.codegen.model.MapperModel;
import com.dfec.codegen.model.MapperXmlModel;
import lombok.Data;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
@Data
public class JavaGenerationModel implements GenerationModel {

    private String tableName;

    private Table table;

    private GenerationMetadata metadata;

    private EntityModel entity;

    private MapperModel mapper;

    private MapperXmlModel mapperXml;
}
