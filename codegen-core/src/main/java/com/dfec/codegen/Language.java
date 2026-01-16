package com.dfec.codegen;

/**
 * @author: zhangth
 * @date: 2026/1/14 15:22
 */
public enum Language {
    // @formatter:off
    JAVA("java", ".java"),
    KOTLIN("kotlin", ".kt"),
    TYPESCRIPT("typescript", ".ts"),
    JAVASCRIPT("javascript", ".js"),
    // @formatter:on
    ;

    private String name;

    private String extension;

    Language(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
