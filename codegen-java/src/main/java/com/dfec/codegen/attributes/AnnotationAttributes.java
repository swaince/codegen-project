package com.dfec.codegen.attributes;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
@Data
public class AnnotationAttributes {

    /**
     * 短名称
     */
    private String name;

    /**
     * 全类名
     */
    private String className;

    /**
     * 值列表
     */
    private String values;

    /**
     * 额外的导入
     */
    private List<String> additionalImports;

    public AnnotationAttributes(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public AnnotationAttributes(String name, String className, String values) {
        this.name = name;
        this.className = className;
        this.values = values;
    }

    public String getAnnotation() {
        if (values == null || values.isEmpty()) {
            return "@" + name;
        }
        return String.format("@%s(%s)", name, values);
    }

    public void addAdditionalImport(String additionalImport) {
        if (additionalImports == null) {
            additionalImports = new ArrayList<>();
        }
        additionalImports.add(additionalImport);
    }
}
