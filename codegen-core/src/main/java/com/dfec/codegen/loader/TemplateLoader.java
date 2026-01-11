package com.dfec.codegen.loader;

import java.io.IOException;

/**
 * 模板加载器
 * @author zhangth
 * @since 2026/1/10
 */
public interface TemplateLoader {

    /**
     * 加载模板内容
     * @param templateName 模板名称
     * @return 模板内容
     */
    String load(String templateName) throws IOException;
}
