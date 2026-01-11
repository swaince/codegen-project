package com.dfec.codegen.config;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

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


}
