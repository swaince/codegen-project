package com.dfec.codegen.loader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author: zhangth
 * @date: 2026/1/12 9:24
 */
public class FileTemplateLoader implements TemplateLoader {

    private final String base;

    public FileTemplateLoader(String base) {
        this.base = base;
    }

    @Override
    public String load(String templateName) throws IOException {
        Path tplPath = Paths.get(base, templateName);
        return new String(Files.readAllBytes(tplPath), StandardCharsets.UTF_8);
    }
}
