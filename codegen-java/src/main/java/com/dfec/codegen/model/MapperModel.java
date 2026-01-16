package com.dfec.codegen.model;

import com.dfec.codegen.GenerationModel;
import com.dfec.codegen.attributes.AnnotationAttribute;
import lombok.Data;

import java.util.*;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
@Data
public class MapperModel implements GenerationModel {

    /**
     * 类名
     */
    private String name;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 继承的父类
     */
    private String superClass;

    /**
     * 导入包列表
     */
    private Set<String> imports = new TreeSet<>();

    /**
     * 注解列表
     */
    private List<AnnotationAttribute> annotationAttributes = new LinkedList<>();

    /**
     * 注解列表
     */
    private Set<String> annotations = new TreeSet<>();

    /**
     *  输出目录
     */
    private String  outputDir;

    /**
     * 代码
     */
    private String code;

    /**
     * 作者
     */
    private String author;

    /**
     * 创建时间
     */
    private String date;

    /**
     * 是否使用MybatisPlus
     */
    private boolean useMybatisPlus;


    public void addImportPackage(String importPackage) {
        imports.add(importPackage);
    }

    public void addImportPackages(Collection<String> importPackages) {
        this.imports.addAll(importPackages);
    }

    public void addAnnotationAttribute(AnnotationAttribute annotation) {
        annotationAttributes.add(annotation);
    }

    public void addAnnotationAttributes(Collection<AnnotationAttribute> annotations) {
        this.annotationAttributes.addAll(annotations);
    }

    public String getClassName() {
        return String.format("%s.%s", packageName, name);
    }
}
