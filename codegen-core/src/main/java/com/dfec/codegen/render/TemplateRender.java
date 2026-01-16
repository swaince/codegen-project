package com.dfec.codegen.render;

import com.dfec.codegen.GenerationModel;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * 模板渲染器
 *
 * @author zhangth
 * @since 2026/1/10
 */
public interface TemplateRender {

    /**
     * 渲染器名称
     * @return
     */
    String name();


    /**
     * 获取模板后缀
     * @return
     */
    String templateSuffix();

    /**
     * 渲染模板
     *
     * @param template 模板内容
     * @param model    模板填充模型
     * @return 渲染后的内容
     */
    String render(String template, GenerationModel model) throws IOException, TemplateException;
}
