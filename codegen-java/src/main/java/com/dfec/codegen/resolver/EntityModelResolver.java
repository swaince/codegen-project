package com.dfec.codegen.resolver;

import com.dfec.codegen.JavaGenerationModel;
import com.dfec.codegen.attributes.AnnotationAttributes;
import com.dfec.codegen.config.JavaGenerationConfig;
import com.dfec.codegen.config.PackageConfig;
import com.dfec.codegen.config.StrategyConfig;
import com.dfec.codegen.converter.DefaultNameConverter;
import com.dfec.codegen.converter.NameConverter;
import com.dfec.codegen.db.GenerationMetadata;
import com.dfec.codegen.db.Table;
import com.dfec.codegen.model.EntityModel;
import com.dfec.codegen.utils.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public class EntityModelResolver implements ModelResolver<EntityModel>{

    private final NameConverter nameConverter = new DefaultNameConverter();

    @Override
    public EntityModel resolve(GenerationMetadata metadata, Table table) {

        JavaGenerationConfig config = metadata.getConfig();
        StrategyConfig strategy = config.getStrategy();
        PackageConfig packages = config.getPackages();

        String name = table.getName();
        name = StringUtils.trimPrefix(name, strategy.getTablePrefixes());
        name = StringUtils.trimSuffix(name, strategy.getTableSuffixes());

        EntityModel entity = new EntityModel();
        entity.setTable(table);
        String entityName = nameConverter.tableNameToEntityName(name);
        entity.setName(entityName);
        entity.setRemark(table.getRemark());
        entity.setPackageName(packages.getEntityPackage());

        List<AnnotationAttributes> classAnnotations = getClassAnnotations(metadata, table);
        List<AnnotationAttributes> fieldAnnotations = getFieldAnnotations(metadata, table);
        classAnnotations.addAll(fieldAnnotations);
        classAnnotations.forEach(annotation -> {
            entity.addAnnotation(annotation.getAnnotation());
            entity.addImportPackage(annotation.getClassName());
        });

        return entity;
    }

    private List<AnnotationAttributes> getClassAnnotations(GenerationMetadata metadata, Table table) {
        List<AnnotationAttributes> annotations = new LinkedList<>();

        JavaGenerationConfig config = metadata.getConfig();
        StrategyConfig strategy = config.getStrategy();
        if (strategy.isUseLombok()) {
            annotations.add(new AnnotationAttributes("Getter", "lombok.Getter"));
            annotations.add(new AnnotationAttributes("Setter", "lombok.Setter"));
            annotations.add(new AnnotationAttributes("Data", "lombok.Data"));
        }
        if (strategy.isUseMybatisPlus()) {
            annotations.add(new AnnotationAttributes("TableName",
                    "com.baomidou.mybatisplus.annotation.TableName",
                    String.format("\"%s\"",  table.getName())));
        }
        return annotations;
    }

    private List<AnnotationAttributes> getFieldAnnotations(GenerationMetadata metadata, Table table) {
        List<AnnotationAttributes> annotations = new LinkedList<>();
        JavaGenerationConfig config = metadata.getConfig();
        StrategyConfig strategy = config.getStrategy();


        return annotations;
    }

}
