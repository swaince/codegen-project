package com.dfec.codegen.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
class DefaultNameConverterTest {

    private NameConverter nameConverter = new DefaultNameConverter();

    @Test
    void tableNameToEntityName() {
        String entityname = nameConverter.tableNameToEntityName("t_student");
        Assertions.assertEquals("TStudent", entityname);

        Assertions.assertEquals("TSystemConfig", nameConverter.tableNameToEntityName("t_system_config"));
    }

    @Test
    void columnNameToFieldName() {
    }

    @Test
    void columnNameToGetterName() {
    }

    @Test
    void columnNameToSetterName() {
    }
}