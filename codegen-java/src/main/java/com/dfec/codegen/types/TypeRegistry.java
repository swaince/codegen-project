package com.dfec.codegen.types;

import com.dfec.codegen.config.JavaGenerationConfig;
import com.dfec.codegen.config.StrategyConfig;
import com.dfec.codegen.db.TableColumn;
import com.dfec.codegen.types.handler.*;

import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: zhangth
 * @date: 2026/1/12 15:43
 */
public class TypeRegistry {

    public final Map<Integer, JavaClass> TYPE_MAP = new HashMap<>();

    private final List<TypeHandler> typeHandlers;

    public TypeRegistry(JavaGenerationConfig config) {
        StrategyConfig strategy = config.getStrategy();
        List<TypeHandler> typeHandlers = strategy.getTypeHandlers();
        if (typeHandlers == null) {
            typeHandlers = new ArrayList<>();
        } else {
            typeHandlers = new ArrayList<>(typeHandlers);
        }

        // 优先处理
        typeHandlers.add(0, new NumberTypeHandler());
        typeHandlers.add(0, new BitTypeHandler());

        // 三种日期格式只能有一种
        if (strategy.isUseUtilDate()) {
            typeHandlers.add(new DefaultDateTypeHandler());
        } else if (strategy.isUseJava8Date()) {
            typeHandlers.add(new Java8DateTypeHandler());
        } else {
            typeHandlers.add(new SqlDateTypeHandler());
        }

        this.typeHandlers = typeHandlers.stream().sorted(Comparator.comparing(TypeHandler::getOrder)).collect(Collectors.toList());
        initTypes();
    }

    public void initTypes() {
        //byte
        TYPE_MAP.put(Types.TINYINT, createJavaClass(JavaType.BYTE));
        //short
        TYPE_MAP.put(Types.SMALLINT, createJavaClass(JavaType.SHORT));
        // int
        TYPE_MAP.put(Types.INTEGER, createJavaClass(JavaType.INTEGER));
        //long
        TYPE_MAP.put(Types.BIGINT, createJavaClass(JavaType.LONG));
        //float
        TYPE_MAP.put(Types.FLOAT, createJavaClass(JavaType.FLOAT));
        TYPE_MAP.put(Types.REAL, createJavaClass(JavaType.FLOAT));
        // double
        TYPE_MAP.put(Types.DOUBLE, createJavaClass(JavaType.DOUBLE));
        // boolean
        TYPE_MAP.put(Types.BIT, createJavaClass(JavaType.BOOLEAN));
        TYPE_MAP.put(Types.BOOLEAN, createJavaClass(JavaType.BOOLEAN));

        // byte[]
        TYPE_MAP.put(Types.BINARY, createJavaClass(JavaType.BYTE_ARRAY));
        TYPE_MAP.put(Types.BLOB, createJavaClass(JavaType.BYTE_ARRAY));
        TYPE_MAP.put(Types.LONGVARBINARY, createJavaClass(JavaType.BYTE_ARRAY));
        TYPE_MAP.put(Types.VARBINARY, createJavaClass(JavaType.BYTE_ARRAY));
        //string
        TYPE_MAP.put(Types.CHAR, createJavaClass(JavaType.STRING));
        TYPE_MAP.put(Types.CLOB, createJavaClass(JavaType.STRING));
        TYPE_MAP.put(Types.VARCHAR, createJavaClass(JavaType.STRING));
        TYPE_MAP.put(Types.LONGVARCHAR, createJavaClass(JavaType.STRING));
        TYPE_MAP.put(Types.LONGNVARCHAR, createJavaClass(JavaType.STRING));
        TYPE_MAP.put(Types.NCHAR, createJavaClass(JavaType.STRING));
        TYPE_MAP.put(Types.NCLOB, createJavaClass(JavaType.STRING));
        TYPE_MAP.put(Types.NVARCHAR, createJavaClass(JavaType.STRING));
        //date
        TYPE_MAP.put(Types.DATE, createJavaClass(JavaType.DATE));
        TYPE_MAP.put(Types.TIME_WITH_TIMEZONE, createJavaClass(JavaType.LOCAL_TIME));
        TYPE_MAP.put(Types.TIMESTAMP_WITH_TIMEZONE, createJavaClass(JavaType.LOCAL_DATE_TIME));
        //timestamp
        TYPE_MAP.put(Types.TIMESTAMP, createJavaClass(JavaType.TIMESTAMP));
        //bigDecimal
        TYPE_MAP.put(Types.NUMERIC, createJavaClass(JavaType.BIG_DECIMAL));
        TYPE_MAP.put(Types.DECIMAL, createJavaClass(JavaType.BIG_DECIMAL));
    }

    public JavaClass getType(TableColumn column) {
        Integer type = column.getDataType();
        JavaClass defaultType = TYPE_MAP.getOrDefault(type, createJavaClass(JavaType.OBJECT));
        if (typeHandlers != null && !typeHandlers.isEmpty()) {
            for (TypeHandler typeHandler : typeHandlers) {
                JavaClass javaClass = typeHandler.handle(defaultType, column);
                if (javaClass != null) {
                    return javaClass;
                }
            }
        }
        return defaultType;
    }

    public void registry(com.dfec.codegen.types.Types type, String simpleName, String className) {
        TYPE_MAP.put(type.getValue(), createJavaClass(simpleName, className));
    }

    public JavaClass createJavaClass(JavaType javaType) {
        return new JavaClass(javaType.getSimpleName(), javaType.getClassName());
    }

    public JavaClass createJavaClass(String simpleName, String className) {
        return new JavaClass(simpleName, className);
    }
}
