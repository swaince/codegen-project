package com.dfec.codegen;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public abstract class AbstractCodeGenerator <T> implements CodeGenerator {

    public abstract boolean support(GenerationConfig config);

    @Override
    @SuppressWarnings("unchecked")
    public GenerationResult generate(GenerationConfig config) {
        return doGenerate((T)config);
    }

    protected abstract GenerationResult doGenerate(T config);
}
