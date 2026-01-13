package com.dfec.codegen.config;

import com.dfec.codegen.types.handler.TypeHandler;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.Set;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
@Data
@Builder
public class StrategyConfig {

    @Builder.Default
    private boolean useLombok = false;

    @Builder.Default
    private boolean useMybatisPlus = true;

    @Singular("addTablePrefix")
    private Set<String> tablePrefixes;
    @Singular("addTableSuffix")
    private Set<String> tableSuffixes;
    @Singular("addColumnPrefix")
    private Set<String> columnPrefixes;
    @Singular("addColumnSuffix")
    private Set<String> columnSuffixes;

    @Singular("addTypeHandler")
    private List<TypeHandler> typeHandlers;

    /**
     * date -> java.sql.Date
     */
    @Builder.Default
    private boolean useSqlDate = false;

    /**
     * date -> java.util.Date
     */
    @Builder.Default
    private boolean useUtilDate = true;

    /**
     * date -> java.time.LocalDateTime
     */
    @Builder.Default
    private boolean useJava8Date = false;

    /**
     * mapper 配置
     */
    @Builder.Default
    private MapperConfig mapper = new MapperConfig();

    /**
     * entity 配置
     */
    @Builder.Default
    private EntityConfig entity = new EntityConfig();
}
