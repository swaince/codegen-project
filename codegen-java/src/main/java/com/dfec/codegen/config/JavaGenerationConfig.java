package com.dfec.codegen.config;

import com.dfec.codegen.GenerationConfig;
import lombok.Builder;
import lombok.Data;

import java.util.function.Consumer;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
@Data
@Builder
public class JavaGenerationConfig implements GenerationConfig {

    /**
     * 作者
     */
    private String author;

    /**
     * 生成的基础路径
     */
    @Builder.Default
    private String base = System.getProperty("user.dir");

    /**
     * 数据库配置
     */
    @Builder.Default
    private DatabaseConfig database = DatabaseConfig.builder().build();

    /**
     * 包配置
     */
    @Builder.Default
    private PackageConfig packages = PackageConfig.builder().build();

    /**
     * 生成策略配置
     */
    @Builder.Default
    private StrategyConfig strategy = StrategyConfig.builder().build();

    public JavaGenerationConfig configureDatabase(Consumer<DatabaseConfig.DatabaseConfigBuilder> configure) {
        DatabaseConfig.DatabaseConfigBuilder builder = DatabaseConfig.builder();
        configure.accept(builder);
        this.database = builder.build();
        return this;
    }

    public JavaGenerationConfig configurePackage(Consumer<PackageConfig.PackageConfigBuilder> configure) {
        PackageConfig.PackageConfigBuilder builder = PackageConfig.builder();
        configure.accept(builder);
        this.packages = builder.build();
        return this;
    }

    public JavaGenerationConfig configureStrategy(Consumer<StrategyConfig.StrategyConfigBuilder> configure) {
        StrategyConfig.StrategyConfigBuilder builder = StrategyConfig.builder();
        configure.accept(builder);
        this.strategy = builder.build();
        return this;
    }

    public static class JavaGenerationConfigBuilder {
    }


}
