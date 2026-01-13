package com.dfec.codegen.model;

import com.dfec.codegen.GenerationModel;
import com.dfec.codegen.attributes.AnnotationAttributes;
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
     * 导入包列表
     */
    private Set<String> importPackages = new TreeSet<>();

    /**
     * 注解列表
     */
    private List<AnnotationAttributes> annotations = new LinkedList<>();


    public void addImportPackage(String importPackage) {
        importPackages.add(importPackage);
    }

    public void addImportPackages(Collection<String> importPackages) {
        this.importPackages.addAll(importPackages);
    }

    public void addAnnotation(AnnotationAttributes annotation) {
        annotations.add(annotation);
    }

    public void addAnnotations(Collection<AnnotationAttributes> annotations) {
        this.annotations.addAll(annotations);
    }
}
