package com.dfec.codegen.config;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

/**
 * @author: zhangth
 * @date: 2026/1/13 10:43
 */
@Data
@Builder(builderClassName = "Builder")
public class MapperConfig {


    /**
     * Mapper 父类
     */
    @Default
    private String superClass = "com.baomidou.mybatisplus.core.mapper.BaseMapper";

    /**
     * 是否使用泛型
     */
    @Default
    private boolean generic = true;

}
