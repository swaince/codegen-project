package com.dfec.codegen.resolver;

import com.dfec.codegen.JavaGenerationModel;
import com.dfec.codegen.attributes.AnnotationAttributes;
import com.dfec.codegen.config.JavaGenerationConfig;
import com.dfec.codegen.config.PackageConfig;
import com.dfec.codegen.converter.DefaultNameConverter;
import com.dfec.codegen.converter.NameConverter;
import com.dfec.codegen.db.GenerationMetadata;
import com.dfec.codegen.db.Table;
import com.dfec.codegen.model.EntityModel;
import com.dfec.codegen.model.MapperModel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public class MapperModelResolver implements ModelResolver<MapperModel> {

    private NameConverter nameConverter = new DefaultNameConverter();

    @Override
    public MapperModel resolve(JavaGenerationModel model, Table table) {
        GenerationMetadata metadata = model.getMetadata();
        JavaGenerationConfig config = metadata.getConfig();
        PackageConfig packages = config.getPackages();
        String mapper = packages.getMapper();
        EntityModel entity = model.getEntity();

        MapperModel result = new MapperModel();
        result.setName(entity.getName() + nameConverter.tableNameToEntityName(mapper));
        String mapperPackage = packages.getMapperPackage();
        result.setPackageName(mapperPackage);
        result.setRemark(table.getRemark());
        List<String> defaultImports = getDefaultImports(model, table);
        result.addImportPackages(defaultImports);

        List<AnnotationAttributes> annotations = getClassAnnotationAttributes(model, table);
        result.addAnnotations(annotations);

        List<String> annotationImports = annotations.stream().flatMap(annotation -> {
            List<String> imports = new LinkedList<>();
            imports.add(annotation.getClassName());
            if (annotation.getAdditionalImports() != null) {
                imports.addAll(annotation.getAdditionalImports());
            }

            return imports.stream();
        }).distinct().collect(Collectors.toList());

        result.addImportPackages(annotationImports);

        String outputDir = getOutputDir(mapperPackage, config.getBase(), result.getName() +".java");
        entity.setOutputDir(outputDir);
        return result;
    }

    private List<AnnotationAttributes> getClassAnnotationAttributes(JavaGenerationModel model, Table table) {
        List<AnnotationAttributes> result = new LinkedList<>();
        result.add(new AnnotationAttributes("Mapper", "org.apache.ibatis.annotations.Mapper"));
        return result;
    }

    private List<String> getDefaultImports(JavaGenerationModel model, Table table) {
        List<String> result = new LinkedList<>();
        result.add(model.getMetadata().getConfig().getStrategy().getMapper().getSuperClass());

        return result;
    }
}
