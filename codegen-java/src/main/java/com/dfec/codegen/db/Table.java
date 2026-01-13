package com.dfec.codegen.db;

import lombok.Data;

import java.util.List;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
@Data
public class Table {

    private String name;

    private String type;

    private String remark;

    private List<TableColumn> columns;

    private List<TableColumn> primaryKeys;
}
