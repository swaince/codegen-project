package com.dfec.codegen.config;

import com.dfec.codegen.types.IdType;
import lombok.Data;

/**
 * @author: zhangth
 * @date: 2026/1/13 10:43
 */
@Data
public class EntityConfig {

    private IdType idType = IdType.AUTO;
}
