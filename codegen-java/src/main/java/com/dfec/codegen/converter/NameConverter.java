package com.dfec.codegen.converter;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public interface NameConverter {

    /**
     * 表名转实体名
     * @param tableName 表名
     * @return
     */
    String tableNameToEntityName(String tableName);

    /**
     * 列名转属性名
     * @param columnName 列名
     * @return
     */
    String columnNameToFieldName(String columnName);

    /**
     * 列名转 Getter 名
     * @param columnName 列名
     * @return
     */
    String columnNameToGetterName(String columnName);

    /**
     * 列名转 Setter 名
     * @param columnName
     * @return
     */
    String columnNameToSetterName(String columnName);
}
