package com.dfec.codegen.attributes;

import lombok.Data;

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
        return String.format("@%s(%s)",  name, values);
    }
}
