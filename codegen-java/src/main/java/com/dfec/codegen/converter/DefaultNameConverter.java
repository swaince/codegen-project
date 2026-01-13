package com.dfec.codegen.converter;

import org.apache.commons.text.CaseUtils;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public class DefaultNameConverter implements NameConverter {
    @Override
    public String tableNameToEntityName(String tableName) {
        return CaseUtils.toCamelCase(tableName, true, '_');
    }

    @Override
    public String columnNameToFieldName(String columnName) {
        return CaseUtils.toCamelCase(columnName, false, '_');
    }

    @Override
    public String columnNameToGetterName(String columnName, Class<?> clazz) {
        String getPrefix = "get";
        String property = getBeanName(columnName);
        if (Boolean.class.equals(clazz) || boolean.class.equals(clazz)) {
            getPrefix = "is";
        }
        return getPrefix + property;
    }

    @Override
    public String columnNameToSetterName(String columnName) {
        String property = getBeanName(columnName);
        return "set" + property;
    }

    public static String getBeanName(String propertyName) {

        if (isLowerCase(propertyName, 0) && isUpperCase(propertyName, 1)) {
            // vPid -> getvPid
            return propertyName;
        } else if (isUpperCase(propertyName, 0) && isLowerCase(propertyName, 1)) {
            // Vpid -> getVpid
            return propertyName;
        } else {
            return propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        }
    }

    public static boolean isLowerCase(String string, int index) {
        return Character.isLowerCase(string.charAt(index));
    }

    public static boolean isUpperCase(String string, int index) {
        return Character.isUpperCase(string.charAt(index));
    }
}
