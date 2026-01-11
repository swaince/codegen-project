package com.dfec.codegen.loader;

import sun.misc.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public class FileTemplateLoader implements TemplateLoader{

    @Override
    public String load(String templateName)throws IOException  {
        ClassLoader loader = getClassLoader();
        try(InputStream inputstream = loader.getResourceAsStream(templateName)) {
            if (inputstream == null) {
                throw new FileNotFoundException(templateName);
            }
            byte[] bytes = IOUtils.readAllBytes(inputstream);
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    private ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
