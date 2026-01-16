package com.dfec.codegen.model;

import com.dfec.codegen.GenerationModel;
import lombok.Data;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
@Data
public class MapperXmlModel implements GenerationModel {

    private String outputDir;

    private String code;
}
