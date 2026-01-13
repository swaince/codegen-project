package com.dfec.codegen.db;

import lombok.Data;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
@Data
public class TableColumn {

    private String name;

    private String tableName;

    private boolean primaryKey;

    private int dataType;

    private String typeName;

    private Integer length;

    private Integer scale;

    private Integer numPrecRadix;

    private Integer nullable;

    private String remark;

    private String columnDef;

    private String autoIncrement;

    private String generatedColumn;
}
