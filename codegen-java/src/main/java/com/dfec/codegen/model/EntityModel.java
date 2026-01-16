package com.dfec.codegen.model;

import com.dfec.codegen.GenerationModel;
import com.dfec.codegen.db.Table;
import com.dfec.codegen.po.JavaBeanProperty;
import com.dfec.codegen.types.JavaClass;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
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

    /**
     * bean 属性列表
     */
    private List<JavaBeanProperty> properties;

    /**
     * 输出目录
     */
    private String outputDir;

    /**
     * 渲染后的代码
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
     * 父类
     */
    private String superClass;

    /**
     * 是否使用泛型
     */
    private boolean generic;

    /**
     * 是否启用序列化
     */
    private boolean serializable;

    /**
     * 是否使用Lombok
     */
    private boolean useLombok;

    public void addImportPackage(String packageName) {
        this.imports.add(packageName);
    }

    public void addImportPackages(String... packageNames) {
        this.imports.addAll(Arrays.asList(packageNames));
    }

    public void addAnnotation(String annotation) {
        this.annotations.add(annotation);
    }

    public void addAnnotations(String... annotations) {
        this.annotations.addAll(Arrays.asList(annotations));
    }

    public String getClassName() {
        return String.format("%s.%s", packageName, name);
    }
}
