package com.dfec.codegen.types;

/**
 * @author: zhangth
 * @date: 2026/1/12 15:14
 */
public class JavaClass {

    private String name;

    private String className;

    public JavaClass(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public JavaClass(String className) {
        this.className = className;
        Class<?> typeClass = getTypeClass();
        if (typeClass != null) {
            this.name = typeClass.getSimpleName();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Class<?> getTypeClass() {
        if (className == null || className.isEmpty()) {
            // 基础类型及java.lang包下的类没有类型，直接返回null
            return null;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isBoolean() {
        return "boolean".equals(name) || "Boolean".equals(name);
    }
}
