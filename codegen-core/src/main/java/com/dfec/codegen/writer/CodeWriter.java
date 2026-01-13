package com.dfec.codegen.writer;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public interface CodeWriter {

    /**
     * 代码写入
     *
     * @param code 代码
     * @param path 文件路径
     */
    void write(String code, String path) throws IOException;

    /**
     * 代码写入流
     *
     * @param code         代码
     * @param outputStream 流
     * @param
     */
    void write(String code, OutputStream outputStream) throws IOException;
}
