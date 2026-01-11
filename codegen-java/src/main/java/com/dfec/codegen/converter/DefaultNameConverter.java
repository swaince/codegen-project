package com.dfec.codegen.converter;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public class DefaultNameConverter implements NameConverter {
    @Override
    public String tableNameToEntityName(String tableName) {
        if (tableName == null || tableName.trim().isEmpty()) {
            return "";
        }

        // 处理下划线转驼峰
        StringBuilder entityName = new StringBuilder();
        boolean toUpper = true;

        for (char c : tableName.toCharArray()) {
            if (c == '_') {
                toUpper = true;
            } else {
                if (toUpper) {
                    entityName.append(Character.toUpperCase(c));
                    toUpper = false;
                } else {
                    entityName.append(Character.toLowerCase(c));
                }
            }
        }

        // 确保首字母大写
        if (entityName.length() > 0) {
            char firstChar = entityName.charAt(0);
            if (!Character.isUpperCase(firstChar)) {
                entityName.setCharAt(0, Character.toUpperCase(firstChar));
            }
        }
        return entityName.toString();
    }

    @Override
    public String columnNameToFieldName(String columnName) {
        return "";
    }

    @Override
    public String columnNameToGetterName(String columnName) {
        return "";
    }

    @Override
    public String columnNameToSetterName(String columnName) {
        return "";
    }
}
