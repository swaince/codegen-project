package com.dfec.codegen.render;

import com.dfec.codegen.GenerationModel;
import freemarker.cache.ByteArrayTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public class FreemarkerTemplateRender implements TemplateRender {

    private FreemarkerConfiguration configuration;

    public FreemarkerTemplateRender(FreemarkerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String render(String template, GenerationModel model) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
        ByteArrayTemplateLoader loader = new ByteArrayTemplateLoader();
        loader.putTemplate("template", template.getBytes(StandardCharsets.UTF_8));
        cfg.setTemplateLoader(loader);
        Template tpl = cfg.getTemplate("template");

        Writer out = new StringWriter();
        tpl.process(model, out);
        return out.toString();
    }

    @Override
    public String name() {
        return "freemarker";
    }

    @Override
    public String templateSuffix() {
        return "ftl";
    }
}
