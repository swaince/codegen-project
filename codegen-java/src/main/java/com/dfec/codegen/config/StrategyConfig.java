package com.dfec.codegen.config;

import com.dfec.codegen.types.handler.TypeHandler;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
@Data
@Builder(builderClassName = "Builder")
public class StrategyConfig {

    @Default
    private boolean useLombok = false;

    @Default
    private boolean useMybatisPlus = true;

    @Singular("addTablePrefix")
    private Set<String> tablePrefixes;
    @Singular("addTableSuffix")
    private Set<String> tableSuffixes;
    @Singular("addColumnPrefix")
    private Set<String> columnPrefixes;
    @Singular("addColumnSuffix")
    private Set<String> columnSuffixes;

    @Singular("addIncludeTable")
    private Set<String> includeTables;

    @Singular("addExcludeTable")
    private Set<String> excludeTables;

    @Singular("addTypeHandler")
    private List<TypeHandler> typeHandlers;

    /**
     * date -> java.sql.Date
     */
    @Default
    private boolean useSqlDate = false;

    /**
     * date -> java.util.Date
     */
    @Default
    private boolean useUtilDate = true;

    /**
     * date -> java.time.LocalDateTime
     */
    @Default
    private boolean useJava8Date = false;

    /**
     * mapper 配置
     */
    @Default
    private MapperConfig mapper = MapperConfig.builder().build();

    /**
     * entity 配置
     */
    @Default
    private EntityConfig entity = EntityConfig.builder().build();

    public static class Builder {

        public Builder configureEntity(Consumer<EntityConfig.Builder> configure) {
            EntityConfig.Builder builder = EntityConfig.builder();
            configure.accept(builder);
            this.entity$value = builder.build();
            this.entity$set = true;
            return this;
        }

        public Builder configureMapper(Consumer<MapperConfig.Builder> configure) {
            MapperConfig.Builder builder = MapperConfig.builder();
            configure.accept(builder);
            this.mapper$value = builder.build();
            this.mapper$set = true;
            return this;
        }
    }

}
