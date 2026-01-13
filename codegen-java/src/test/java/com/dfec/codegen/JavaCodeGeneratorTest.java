package com.dfec.codegen;

import com.dfec.codegen.config.JavaGenerationConfig;
import org.junit.jupiter.api.Test;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
class JavaCodeGeneratorTest {

    @Test
    void generate() {

        JavaGenerationConfig config = JavaGenerationConfig.builder().build();

        config.setAuthor("zhangth");
        config
                .configureStrategy(builder -> {
                    builder
                            .useLombok(true)
                            .addTablePrefix("t_")
                            .addColumnPrefix("is")
                            .addColumnSuffix("flag");
                })
                .configureDatabase(builder -> {
                    builder.jdbcUrl("jdbc:mysql://192.168.66.118:3307/fh-system")
                            .username("root")
                            .password("123456");
                });
        JavaCodeGenerator generator = new JavaCodeGenerator();
        GenerationResult result = generator.generate(config);
        System.out.println(result);
    }
}