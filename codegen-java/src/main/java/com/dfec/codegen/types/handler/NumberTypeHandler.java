package com.dfec.codegen.types.handler;

import com.dfec.codegen.db.TableColumn;
import com.dfec.codegen.types.JavaClass;
import com.dfec.codegen.types.JavaType;

import java.sql.Types;

/**
 * @author: zhangth
 * @date: 2026/1/13 9:39
 */
public class NumberTypeHandler implements TypeHandler {
    @Override
    public JavaClass handle(JavaClass defaultType, TableColumn column) {

        if (column.getDataType() != Types.NUMERIC && column.getDataType() != Types.DECIMAL) {
            return null;
        }

        if (column.getLength() > 18 && column.getScale() > 0) {
            return defaultType;
        } else if (column.getLength() > 9) {
            return createJavaClass(JavaType.LONG);
        } else if (column.getLength() > 4) {
            return createJavaClass(JavaType.INTEGER);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
