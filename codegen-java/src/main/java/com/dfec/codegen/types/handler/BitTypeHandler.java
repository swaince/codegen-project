package com.dfec.codegen.types.handler;

import com.dfec.codegen.db.TableColumn;
import com.dfec.codegen.types.JavaClass;
import com.dfec.codegen.types.JavaType;

import java.sql.Types;

/**
 * @author: zhangth
 * @date: 2026/1/13 9:35
 */
public class BitTypeHandler implements TypeHandler {
    @Override
    public JavaClass handle(JavaClass defaultType, TableColumn column) {
        if (Types.BIT == column.getDataType() && column.getLength() == 1) {
            return createJavaClass(JavaType.BOOLEAN);
        } else if (Types.BIT == column.getDataType() && column.getLength() > 1) {
            return createJavaClass(JavaType.BYTE_ARRAY);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
