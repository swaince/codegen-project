package com.dfec.codegen.loader;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public class ClassLoaderTemplateLoader implements TemplateLoader {

    @Override
    public String load(String templateName) throws IOException {
        try (InputStream inputstream = getResourceAsStream(getClass(), templateName)) {
            if (inputstream == null) {
                throw new FileNotFoundException(templateName);
            }

            byte[] bytes = readAllBytes(inputstream);
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    public static InputStream getResourceAsStream(Class<?> claz, String name) {

        while (name.startsWith("/")) {
            name = name.substring(1);
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is;
        if (classLoader == null) {
            classLoader = claz.getClassLoader();
            is = classLoader.getResourceAsStream(name);
        } else {
            is = classLoader.getResourceAsStream(name);
            if (is == null) {
                classLoader = claz.getClassLoader();
                if (classLoader != null) {
                    is = classLoader.getResourceAsStream(name);
                }
            }
        }

        return is;
    }

    private byte[] readAllBytes(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            byte[] data = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            return buffer.toByteArray();
        }
    }
}
