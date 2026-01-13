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
import com.dfec.codegen.db.TableColumn;
import com.dfec.codegen.model.EntityModel;
import com.dfec.codegen.po.JavaBeanProperty;
import com.dfec.codegen.types.JavaClass;
import com.dfec.codegen.types.TypeRegistry;
import com.dfec.codegen.utils.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public class EntityModelResolver implements ModelResolver<EntityModel> {

    private final NameConverter nameConverter = new DefaultNameConverter();

    @Override
    public EntityModel resolve(JavaGenerationModel model, Table table) {
        GenerationMetadata metadata = model.getMetadata();
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
        String entityPackage = packages.getEntityPackage();
        entity.setPackageName(entityPackage);
        List<JavaBeanProperty> properties = getJavaBeanProperties(metadata, table);

        String[] fieldImports = getFieldImportPackages(properties);
        entity.addImportPackages(fieldImports);

        entity.setProperties(properties);
        resolveAnnotations(metadata, table, entity);

        String baseDir = getOutputDir(entityPackage, config.getBase(), entityName + ".java");
        entity.setOutputDir(baseDir);

        return entity;
    }

    private static String[] getFieldImportPackages(List<JavaBeanProperty> properties) {
        return properties.stream().flatMap(property -> {
            List<AnnotationAttributes> annotations = property.getAnnotations();
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            return annotations.stream().flatMap(annotation -> {
                List<String> result = new ArrayList<>();
                result.add(annotation.getClassName());
                if (annotation.getAdditionalImports() != null) {
                    result.addAll(annotation.getAdditionalImports());
                }
                return result.stream();
            });
        }).distinct().toArray(String[]::new);
    }

    private List<JavaBeanProperty> getJavaBeanProperties(GenerationMetadata metadata, Table table) {

        StrategyConfig strategy = metadata.getConfig().getStrategy();

        TypeRegistry typeRegistry = new TypeRegistry(metadata.getConfig());

        return table.getColumns().stream().map(column -> {
            JavaBeanProperty property = new JavaBeanProperty();
            String columnName = column.getName();
            columnName = StringUtils.trimPrefix(columnName, strategy.getColumnPrefixes());
            columnName = StringUtils.trimSuffix(columnName, strategy.getColumnSuffixes());
            String name = nameConverter.columnNameToFieldName(columnName);
            property.setName(name);
            // 根据列类型转换类型
            JavaClass type = typeRegistry.getType(column);
            property.setType(type);
            property.setGetterName(nameConverter.columnNameToGetterName(name, type.isBoolean() ? Boolean.class : type.getTypeClass()));
            property.setSetterName(nameConverter.columnNameToSetterName(name));
            List<AnnotationAttributes> fieldAnnotations = getFieldAnnotations(metadata, column);
            property.setAnnotations(fieldAnnotations);
            property.setRemark(column.getRemark());
            property.setColumn(column);
            return property;
        }).collect(Collectors.toList());
    }

    private void resolveAnnotations(GenerationMetadata metadata, Table table, EntityModel entity) {
        List<AnnotationAttributes> classAnnotations = getClassAnnotations(metadata, table);
        classAnnotations.forEach(annotation -> {
            entity.addAnnotation(annotation.getAnnotation());
            entity.addImportPackage(annotation.getClassName());

            // 导入注解依赖
            List<String> additionalImports = annotation.getAdditionalImports();
            if (additionalImports != null) {
                annotation.getAdditionalImports().forEach(entity::addImportPackage);
            }
        });
    }

    /**
     * 处理类注解
     *
     * @param metadata 元信息
     * @param table    表信息
     * @return
     */
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
                    String.format("\"%s\"", table.getName())));
        }
        return annotations;
    }

    /**
     * 处理字段属性注解
     *
     * @param metadata 元信息
     * @param column   列信息
     * @return
     */
    private List<AnnotationAttributes> getFieldAnnotations(GenerationMetadata metadata, TableColumn column) {
        List<AnnotationAttributes> annotations = new LinkedList<>();
        JavaGenerationConfig config = metadata.getConfig();
        StrategyConfig strategy = config.getStrategy();
        if (!strategy.isUseMybatisPlus()) {
            return annotations;
        }

        Set<String> keywords = metadata.getKeywords();
        String quoteString = "";
        if (keywords.contains(column.getName())) {
            // 关键字转义
            quoteString = metadata.getIdentifierQuoteString();
        }
        if (quoteString == null) {
            quoteString = "";
        }

        if (column.isPrimaryKey()) {
            String values = String.format("value = \"%s%s%s\", type = IdType.%s", quoteString, column.getName(), quoteString, strategy.getEntity().getIdType().getKey());
            AnnotationAttributes tableId = new AnnotationAttributes("TableId", "com.baomidou.mybatisplus.annotation.TableId", values);
            tableId.addAdditionalImport("com.baomidou.mybatisplus.annotation.IdType");
            annotations.add(tableId);
        } else {
            annotations.add(new AnnotationAttributes("TableField", "com.baomidou.mybatisplus.annotation.TableField",
                    String.format("\"%s%s%s\"", quoteString, column.getName(), quoteString)));
        }
        return annotations;
    }

}
