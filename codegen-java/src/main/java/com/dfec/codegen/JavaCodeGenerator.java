package com.dfec.codegen;

import com.dfec.codegen.config.DatabaseConfig;
import com.dfec.codegen.config.JavaGenerationConfig;
import com.dfec.codegen.db.DefaultGenerationMetadataQuery;
import com.dfec.codegen.db.GenerationMetadata;
import com.dfec.codegen.db.GenerationMetadataQuery;
import com.dfec.codegen.db.Table;
import com.dfec.codegen.loader.ClassLoaderTemplateLoader;
import com.dfec.codegen.loader.TemplateLoader;
import com.dfec.codegen.model.EntityModel;
import com.dfec.codegen.model.MapperModel;
import com.dfec.codegen.model.MapperXmlModel;
import com.dfec.codegen.parser.ClasspathTemplatePathParser;
import com.dfec.codegen.render.FreemarkerConfiguration;
import com.dfec.codegen.render.FreemarkerTemplateRender;
import com.dfec.codegen.render.TemplateRender;
import com.dfec.codegen.resolver.EntityModelResolver;
import com.dfec.codegen.resolver.MapperModelResolver;
import com.dfec.codegen.resolver.MapperXmlModelResolver;
import com.dfec.codegen.resolver.ModelResolver;
import com.dfec.codegen.writer.CodeWriter;
import com.dfec.codegen.writer.DefaultCodeWriter;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public class JavaCodeGenerator extends AbstractCodeGenerator<JavaGenerationConfig> {

    @Override
    public boolean support(GenerationConfig config) {
        return config.getClass().isAssignableFrom(JavaGenerationConfig.class);
    }

    @Override
    protected GenerationResult doGenerate(JavaGenerationConfig config) {
        DatabaseConfig database = config.getDatabase();
        GenerationMetadataQuery metadataQuery = database.getMetadataQuery();
        if (metadataQuery == null) {
            metadataQuery = new DefaultGenerationMetadataQuery(config);
        }

        GenerationMetadata metadata = metadataQuery.query();
        metadata.setConfig(config);
        List<JavaGenerationModel> models = metadataToJavaModel(metadata);

        return javaModelToGenerationResult(models, metadata);
    }

    public List<JavaGenerationModel> metadataToJavaModel(GenerationMetadata metadata) {

        JavaGenerationConfig config = metadata.getConfig();
        ModelResolver<EntityModel> entityResolver = config.getEntityResolver();
        ModelResolver<MapperModel> mapperResolver = config.getMapperResolver();
        ModelResolver<MapperXmlModel> mapperXmlResolver = config.getMapperXmlResolver();
        TemplateRender render = config.getRender();
        ClasspathTemplatePathParser pathParser = new ClasspathTemplatePathParser(config.getLanguage().getName(), render.templateSuffix());

        List<Table> tables = metadata.getTables();
        return tables.stream().map(table -> {
            JavaGenerationModel model = new JavaGenerationModel();
            model.setTable(table);
            model.setTableName(table.getName());
            model.setMetadata(metadata);

            EntityModel entity = entityResolver.resolve(model, table);
            model.setEntity(entity);
            String entityCode = getAndWriteCode(model, pathParser.parse("entity"), entity.getOutputDir());
            entity.setCode(entityCode);

            MapperModel mapper = mapperResolver.resolve(model, table);
            model.setMapper(mapper);
            String mapperCode = getAndWriteCode(model, pathParser.parse("mapper"), mapper.getOutputDir());
            mapper.setCode(mapperCode);

            MapperXmlModel mapperXml = mapperXmlResolver.resolve(model, table);
            model.setMapperXml(mapperXml);
            String mapperXmlCode = getAndWriteCode(model, pathParser.parse("mapperXml"), mapperXml.getOutputDir());
            mapperXml.setCode(mapperXmlCode);

            model.setTable(table);
            model.setTableName(table.getName());
            return model;
        }).collect(Collectors.toList());
    }

    private JavaGenerationResult javaModelToGenerationResult(List<JavaGenerationModel> models, GenerationMetadata metadata) {
        JavaGenerationResult result = new JavaGenerationResult();
        result.setMetadata(metadata);
        Map<String, JavaGenerationModel> modelMap = models.stream()
                .collect(Collectors.toMap(JavaGenerationModel::getTableName, Function.identity()));
        result.setModels(modelMap);
        return result;
    }

    private String getAndWriteCode(JavaGenerationModel model, String templatePath, String writePath) {
        try {

            GenerationMetadata metadata = model.getMetadata();
            JavaGenerationConfig config = metadata.getConfig();
            TemplateLoader loader = config.getLoader();
            TemplateRender render = config.getRender();
            CodeWriter writer = config.getWriter();

            String template = loader.load(templatePath);
            String code = render.render(template, model);
            writer.write(code, writePath);
            return code;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
