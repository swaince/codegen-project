package com.dfec.codegen.model;

import com.dfec.codegen.GenerationModel;
import com.dfec.codegen.db.Table;
import lombok.Data;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
@Data
public class EntityModel implements GenerationModel {

    /**
     * 原始表信息
     */
    private Table table;

    /**
     * 实体名
     */
    private String name;

    /**
     * 报名
     */
    private String packageName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 导入的包
     */
    private Set<String> imports = new TreeSet<>();

    /**
     * 🥱注解
     */
    private Set<String> annotations = new TreeSet<>();


    public void addImportPackage(String packageName){
        this.imports.add(packageName);
    }

    public void addImportPackages(String... packageNames){
        this.imports.addAll(Arrays.asList(packageNames));
    }

    public void addAnnotation(String annotation){
        this.annotations.add(annotation);
    }

    public void addAnnotations(String... annotations){
        this.annotations.addAll(Arrays.asList(annotations));
    }
}
