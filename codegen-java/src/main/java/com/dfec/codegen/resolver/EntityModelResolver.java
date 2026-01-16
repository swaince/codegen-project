package com.dfec.codegen.resolver;

import com.dfec.codegen.JavaGenerationModel;
import com.dfec.codegen.Language;
import com.dfec.codegen.attributes.AnnotationAttribute;
import com.dfec.codegen.config.EntityConfig;
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
import com.dfec.codegen.types.Types;
import com.dfec.codegen.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
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

        Language language = config.getLanguage();
        String baseDir = getOutputDir(config.getBase(), entityPackage, language, entityName);
        entity.setOutputDir(baseDir);

        // 整合自定义配置
        setAdditionalProperties(entity, config);

        return entity;
    }

    private static void setAdditionalProperties(EntityModel entity, JavaGenerationConfig config) {
        StrategyConfig strategy = config.getStrategy();
        entity.setAuthor(config.getAuthor());
        SimpleDateFormat sdf = new SimpleDateFormat(config.getDateFormat());
        entity.setDate(sdf.format(new Date()));
        EntityConfig entityConfig = strategy.getEntity();
        String superClass = entityConfig.getSuperClass();
        if (superClass != null && !superClass.isEmpty()) {
            JavaClass javaClass = new JavaClass(superClass);
            entity.setSuperClass(javaClass.getName());
            entity.addImportPackages(javaClass.getClassName());
        }
        entity.setSerializable(entityConfig.isSerializable());
        if (entityConfig.isSerializable()) {
            entity.addImportPackages("java.io.Serializable");
        }
        entity.setGeneric(entityConfig.isGeneric());
        entity.setUseLombok(strategy.isUseLombok());
    }

    private static String[] getFieldImportPackages(List<JavaBeanProperty> properties) {
        return properties.stream().flatMap(property -> {
            List<String> result = new LinkedList<>();
            if (property.getType() != null && property.getType().getClassName() != null) {
                result.add(property.getType().getClassName());
            }

            List<AnnotationAttribute> annotations = property.getAnnotationAttributes();
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            Set<String> annotationImports = annotations.stream().flatMap(annotation -> {
                List<String> importItems = new ArrayList<>();
                importItems.add(annotation.getClassName());
                if (annotation.getAdditionalImports() != null) {
                    importItems.addAll(annotation.getAdditionalImports());
                }
                return importItems.stream();
            }).collect(Collectors.toSet());
            result.addAll(annotationImports);
            return result.stream();
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
            List<AnnotationAttribute> fieldAnnotations = getFieldAnnotations(metadata, column);

            Set<String> annotations = fieldAnnotations.stream().map(annotation -> annotation.getAnnotation()).collect(Collectors.toSet());
            property.setAnnotationAttributes(fieldAnnotations);
            property.setAnnotations(annotations);
            property.setRemark(column.getRemark());
            property.setColumn(column);
            Types jdbcType = Types.getByType(column.getDataType());
            if (jdbcType != null) {
                property.setJdbcType(jdbcType.getName());
            }
            return property;
        }).collect(Collectors.toList());
    }

    private void resolveAnnotations(GenerationMetadata metadata, Table table, EntityModel entity) {
        List<AnnotationAttribute> classAnnotations = getClassAnnotations(metadata, table);
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
    private List<AnnotationAttribute> getClassAnnotations(GenerationMetadata metadata, Table table) {
        List<AnnotationAttribute> annotations = new LinkedList<>();

        JavaGenerationConfig config = metadata.getConfig();
        StrategyConfig strategy = config.getStrategy();
        if (strategy.isUseLombok()) {
            annotations.add(new AnnotationAttribute("Getter", "lombok.Getter"));
            annotations.add(new AnnotationAttribute("Setter", "lombok.Setter"));
            annotations.add(new AnnotationAttribute("Data", "lombok.Data"));
        }
        if (strategy.isUseMybatisPlus()) {
            annotations.add(new AnnotationAttribute("TableName",
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
    private List<AnnotationAttribute> getFieldAnnotations(GenerationMetadata metadata, TableColumn column) {
        List<AnnotationAttribute> annotations = new LinkedList<>();
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
            AnnotationAttribute tableId = new AnnotationAttribute("TableId", "com.baomidou.mybatisplus.annotation.TableId", values);
            tableId.addAdditionalImport("com.baomidou.mybatisplus.annotation.IdType");
            annotations.add(tableId);
        } else {
            annotations.add(new AnnotationAttribute("TableField", "com.baomidou.mybatisplus.annotation.TableField",
                    String.format("\"%s%s%s\"", quoteString, column.getName(), quoteString)));
        }
        return annotations;
    }

}
