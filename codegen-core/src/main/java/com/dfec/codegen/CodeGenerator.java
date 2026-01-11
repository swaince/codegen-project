package com.dfec.codegen;

import java.util.List;

/**
 * 代码生成器
 * @author zhangth
 * @since 2026/1/10
 */
public interface CodeGenerator {

    /**
     * 代码生成
     * @param config 配置信息
     * @return 生成结果
     */
    GenerationResult generate(GenerationConfig config);
}
