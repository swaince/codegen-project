package com.dfec.codegen.resolver;

import com.dfec.codegen.JavaGenerationModel;
import com.dfec.codegen.attributes.AnnotationAttribute;
import com.dfec.codegen.config.JavaGenerationConfig;
import com.dfec.codegen.config.PackageConfig;
import com.dfec.codegen.config.StrategyConfig;
import com.dfec.codegen.converter.DefaultNameConverter;
import com.dfec.codegen.converter.NameConverter;
import com.dfec.codegen.db.GenerationMetadata;
import com.dfec.codegen.db.Table;
import com.dfec.codegen.model.EntityModel;
import com.dfec.codegen.model.MapperModel;
import com.dfec.codegen.types.JavaClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
        String mapperName = packages.getMapper();
        EntityModel entity = model.getEntity();

        MapperModel mapper = new MapperModel();
        mapper.setName(entity.getName() + nameConverter.tableNameToEntityName(mapperName));
        String mapperPackage = packages.getMapperPackage();
        mapper.setPackageName(mapperPackage);
        mapper.setRemark(table.getRemark());
        List<String> defaultImports = getDefaultImports(model, table);
        mapper.addImportPackages(defaultImports);

        List<AnnotationAttribute> annotationAttributes = getClassAnnotationAttributes(model, table);
        mapper.addAnnotationAttributes(annotationAttributes);
        Set<String> annotations = annotationAttributes.stream().map(AnnotationAttribute::getAnnotation).collect(Collectors.toSet());
        mapper.setAnnotations(annotations);
        List<String> annotationImports = annotationAttributes.stream().flatMap(annotation -> {
            List<String> imports = new LinkedList<>();
            imports.add(annotation.getClassName());
            if (annotation.getAdditionalImports() != null) {
                imports.addAll(annotation.getAdditionalImports());
            }

            return imports.stream();
        }).distinct().collect(Collectors.toList());

        mapper.addImportPackages(annotationImports);

        String outputDir = getOutputDir(config.getBase(), mapperPackage, config.getLanguage(), mapper.getName());
        mapper.setOutputDir(outputDir);

        setAdditionalProperties(mapper, model);
        return mapper;
    }

    private List<AnnotationAttribute> getClassAnnotationAttributes(JavaGenerationModel model, Table table) {
        List<AnnotationAttribute> result = new LinkedList<>();
        result.add(new AnnotationAttribute("Mapper", "org.apache.ibatis.annotations.Mapper"));
        return result;
    }

    private List<String> getDefaultImports(JavaGenerationModel model, Table table) {
        List<String> result = new LinkedList<>();
        result.add(model.getMetadata().getConfig().getStrategy().getMapper().getSuperClass());

        return result;
    }

    private static void setAdditionalProperties(MapperModel mapper, JavaGenerationModel model) {

        JavaGenerationConfig config = model.getMetadata().getConfig();

        StrategyConfig strategy = config.getStrategy();
        mapper.setAuthor(config.getAuthor());
        SimpleDateFormat sdf = new SimpleDateFormat(config.getDateFormat());
        mapper.setDate(sdf.format(new Date()));
        mapper.setUseMybatisPlus(strategy.isUseMybatisPlus());

        // 设置mapper 父类
        String superClass = config.getStrategy().getMapper().getSuperClass();
        if(superClass != null && !superClass.isEmpty()) {
            JavaClass javaClass = new JavaClass(superClass);
            mapper.setSuperClass(javaClass.getName());
            mapper.addImportPackage(javaClass.getClassName());
        }

        mapper.addImportPackage(model.getEntity().getClassName());
    }
}
