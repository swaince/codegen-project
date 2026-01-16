package com.dfec.codegen.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public class DefaultCodeWriter implements CodeWriter {
    @Override
    public void write(String code, String path) throws IOException {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
        } else {

        }
        Files.write(filePath, code.getBytes());
    }

    @Override
    public void write(String code, OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            return;
        }

        outputStream.write(code.getBytes(StandardCharsets.UTF_8));
    }
}
