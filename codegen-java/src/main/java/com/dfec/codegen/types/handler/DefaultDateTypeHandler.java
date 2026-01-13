package com.dfec.codegen.types.handler;

import com.dfec.codegen.db.TableColumn;
import com.dfec.codegen.types.JavaClass;
import com.dfec.codegen.types.JavaType;

import java.sql.Types;

/**
 * @author: zhangth
 * @date: 2026/1/13 9:22
 */
public class DefaultDateTypeHandler implements TypeHandler {
    @Override
    public JavaClass handle(JavaClass defaultType, TableColumn column) {
        if (Types.DATE == column.getDataType()) {
            return createJavaClass(JavaType.DATE);
        } else if (Types.TIME == column.getDataType()) {
            return createJavaClass(JavaType.TIME);
        } else if (Types.TIMESTAMP == column.getDataType()) {
            return createJavaClass(JavaType.DATE);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
