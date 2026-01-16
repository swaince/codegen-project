package com.dfec.codegen.config;

import com.dfec.codegen.types.IdType;
import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;

/**
 * @author: zhangth
 * @date: 2026/1/13 10:43
 */
@Data
@Builder(builderClassName = "Builder")
public class EntityConfig {

    @Default
    private IdType idType = IdType.AUTO;

    /**
     * 父类
     */
    private String superClass;

    /**
     * 是否使用泛型
     */
    private boolean generic;

    /**
     * 是否启用序列化
     */
    private boolean serializable;
}
