package com.dfec.codegen.config;

import lombok.Data;

/**
 * @author: zhangth
 * @date: 2026/1/13 10:43
 */
@Data
public class MapperConfig {

    /**
     * Mapper 父类
     */
    private String superClass = "com.baomidou.mybatisplus.core.mapper.BaseMapper";

    /**
     * 是否使用泛型
     */
    private boolean generic = true;

}
