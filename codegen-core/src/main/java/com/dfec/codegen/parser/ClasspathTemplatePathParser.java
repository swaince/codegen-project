package com.dfec.codegen.parser;

/**
 * @author: zhangth
 * @date: 2026/1/14 11:17
 */
public class ClasspathTemplatePathParser implements TemplatePathParser{

    private final String language;

    private final String templateSuffix;

    public ClasspathTemplatePathParser(String language, String templateSuffix) {
        this.language = language;
        this.templateSuffix = templateSuffix;
    }

    @Override
    public String parse(String templateName) {
        return String.format("templates/%s/%s.%s", language, templateName, templateSuffix);
    }
}
