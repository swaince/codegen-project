package com.dfec.codegen.loader;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public class ContentTemplateLoader implements TemplateLoader{

    private final String content;

    public ContentTemplateLoader(String content) {
        this.content = content;
    }

    @Override
    public String load(String templateName) {
        return content;
    }
}
