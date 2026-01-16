package com.dfec.codegen;

import com.dfec.codegen.config.EntityConfig;
import com.dfec.codegen.config.JavaGenerationConfig;
import com.dfec.codegen.types.IdType;
import org.junit.jupiter.api.Test;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
class JavaCodeGeneratorTest {

    @Test
    void generate() {

        JavaGenerationConfig config = JavaGenerationConfig.builder()
                .author("zhangth")
                .base("E:\\CodeRepo\\Fanghub\\codegen-project")
                .language(Language.JAVA)
                .configureStrategy(builder -> {
                    builder
                            .useLombok(false)
                            .addTablePrefix("t_")
                            .addColumnPrefix("is")
                            .addColumnSuffix("flag")
                            .addIncludeTable("t_system_config");
                    builder.configureEntity(entity -> {
                        entity.idType(IdType.INPUT)
                                .generic(true)
                                .serializable(true);
                    });
                })
                .configureDatabase(builder -> {
                    builder.jdbcUrl("jdbc:mysql://192.168.2.160:3307/fh-system")
                            .username("root")
                            .password("123456");
                }).build();
        JavaCodeGenerator generator = new JavaCodeGenerator();
        GenerationResult result = generator.generate(config);
        System.out.println(result);
    }
}