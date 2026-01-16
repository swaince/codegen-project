package com.dfec.codegen.config;

import com.dfec.codegen.GenerationConfig;
import com.dfec.codegen.Language;
import com.dfec.codegen.loader.ClassLoaderTemplateLoader;
import com.dfec.codegen.loader.TemplateLoader;
import com.dfec.codegen.model.EntityModel;
import com.dfec.codegen.model.MapperModel;
import com.dfec.codegen.model.MapperXmlModel;
import com.dfec.codegen.parser.ClasspathTemplatePathParser;
import com.dfec.codegen.render.FreemarkerConfiguration;
import com.dfec.codegen.render.FreemarkerTemplateRender;
import com.dfec.codegen.render.TemplateRender;
import com.dfec.codegen.resolver.EntityModelResolver;
import com.dfec.codegen.resolver.MapperModelResolver;
import com.dfec.codegen.resolver.MapperXmlModelResolver;
import com.dfec.codegen.resolver.ModelResolver;
import com.dfec.codegen.writer.CodeWriter;
import com.dfec.codegen.writer.DefaultCodeWriter;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

import java.util.function.Consumer;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
@Data
@Builder(builderClassName = "Builder")
public class JavaGenerationConfig implements GenerationConfig {

    /**
     * 作者
     */
    @Default
    private String author = "fanghub";

    @Default
    private String dateFormat = "yyyy-MM-dd";

    /**
     * 生成的基础路径
     */
    @Default
    private String base = System.getProperty("user.dir");

    /**
     * 数据库配置
     */
    @Default
    private DatabaseConfig database = DatabaseConfig.builder().build();

    /**
     * 包配置
     */
    @Default
    private PackageConfig packages = PackageConfig.builder().build();

    /**
     * 生成策略配置
     */
    @Default
    private StrategyConfig strategy = StrategyConfig.builder().build();

    /**
     * 是否覆盖文件内容
     */
    @Default
    private boolean overwrite = true;

    @Default
    private Language language = Language.JAVA;

    @Default
    private ModelResolver<EntityModel> entityResolver = new EntityModelResolver();
    @Default
    private ModelResolver<MapperModel> mapperResolver = new MapperModelResolver();
    @Default
    private ModelResolver<MapperXmlModel> mapperXmlResolver = new MapperXmlModelResolver();
    @Default
    private final TemplateLoader loader = new ClassLoaderTemplateLoader();
    @Default
    private final TemplateRender render = new FreemarkerTemplateRender(new FreemarkerConfiguration());
    @Default
    private final CodeWriter writer = new DefaultCodeWriter();

    public static class Builder {


        public Builder configureDatabase(Consumer<DatabaseConfig.Builder> configure) {
            DatabaseConfig.Builder builder = DatabaseConfig.builder();
            configure.accept(builder);
            this.database$value = builder.build();
            this.database$set = true;
            return this;
        }

        public Builder configurePackage(Consumer<PackageConfig.Builder> configure) {
            PackageConfig.Builder builder = PackageConfig.builder();
            configure.accept(builder);
            this.packages$value = builder.build();
            this.packages$set = true;
            return this;
        }

        public Builder configureStrategy(Consumer<StrategyConfig.Builder> configure) {
            StrategyConfig.Builder builder = StrategyConfig.builder();
            configure.accept(builder);
            this.strategy$value = builder.build();
            this.strategy$set = true;
            return this;
        }

    }
}
