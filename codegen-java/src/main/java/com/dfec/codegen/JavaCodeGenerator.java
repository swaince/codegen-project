package com.dfec.codegen;

import com.dfec.codegen.config.DatabaseConfig;
import com.dfec.codegen.config.JavaGenerationConfig;
import com.dfec.codegen.db.DefaultGenerationMetadataQuery;
import com.dfec.codegen.db.GenerationMetadata;
import com.dfec.codegen.db.GenerationMetadataQuery;
import com.dfec.codegen.db.Table;
import com.dfec.codegen.model.EntityModel;
import com.dfec.codegen.model.MapperModel;
import com.dfec.codegen.model.MapperXmlModel;
import com.dfec.codegen.resolver.EntityModelResolver;
import com.dfec.codegen.resolver.MapperModelResolver;
import com.dfec.codegen.resolver.MapperXmlModelResolver;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public class JavaCodeGenerator extends AbstractCodeGenerator<JavaGenerationConfig>{


    private EntityModelResolver entityResolver = new  EntityModelResolver();
    private MapperModelResolver mapperResolver = new  MapperModelResolver();
    private MapperXmlModelResolver mapperXmlResolver = new  MapperXmlModelResolver();

    @Override
    public boolean support(GenerationConfig config) {
        return config.getClass().isAssignableFrom(JavaGenerationConfig.class);
    }

    @Override
    protected GenerationResult doGenerate(JavaGenerationConfig config) {
        DatabaseConfig database = config.getDatabase();
        GenerationMetadataQuery metadataQuery = database.getMetadataQuery();
        if (metadataQuery == null) {
            metadataQuery = new DefaultGenerationMetadataQuery(database);
        }

        GenerationMetadata metadata = metadataQuery.query();
         metadata.setConfig(config);
        JavaGenerationModel model = metadataToJavaModel(metadata);

        return javaModelToGenerationResult(model);
    }

    public JavaGenerationModel metadataToJavaModel(GenerationMetadata metadata) {
        JavaGenerationModel model = new JavaGenerationModel();

        model.setMetadata(metadata);

        List<Table> tables = metadata.getTables();
        List<EntityModel> models = tables.stream().map(table -> {
            EntityModel entityModel = entityResolver.resolve(metadata, table);
            MapperModel mapperModel = mapperResolver.resolve(metadata, table);
            MapperXmlModel mapperXmlModel = mapperXmlResolver.resolve(metadata, table);
            return entityModel;
        }).collect(Collectors.toList());

        return model;
    }

    private JavaGenerationResult javaModelToGenerationResult(JavaGenerationModel model) {
        JavaGenerationResult result = new JavaGenerationResult();

        return result;
    }
}
