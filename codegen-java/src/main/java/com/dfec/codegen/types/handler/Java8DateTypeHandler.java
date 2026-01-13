package com.dfec.codegen.types.handler;

import com.dfec.codegen.db.TableColumn;
import com.dfec.codegen.types.JavaClass;
import com.dfec.codegen.types.JavaType;

import java.sql.Types;

/**
 * @author: zhangth
 * @date: 2026/1/13 9:25
 */
public class Java8DateTypeHandler implements TypeHandler {
    @Override
    public JavaClass handle(JavaClass defaultType, TableColumn column) {
        if (Types.DATE == column.getDataType()) {
            return createJavaClass(JavaType.LOCAL_DATE);
        } else if (Types.TIME == column.getDataType()) {
            return createJavaClass(JavaType.LOCAL_TIME);
        } else if (Types.TIMESTAMP == column.getDataType()) {
            return createJavaClass(JavaType.LOCAL_DATE_TIME);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
