package com.dfec.codegen.types.handler;

import com.dfec.codegen.db.TableColumn;
import com.dfec.codegen.types.JavaClass;
import com.dfec.codegen.types.JavaType;
import com.dfec.codegen.types.Ordered;

/**
 * jdbc 类型处理器
 *
 * @author: zhangth
 * @date: 2026/1/12 17:02
 */
public interface TypeHandler extends Ordered {

    /**
     * 自定义类型处理
     *
     * @param defaultType 默认类型
     * @param column      列信息
     * @return 自定义类型，返回null 或 默认类型 都将使用默认类型
     */
    JavaClass handle(JavaClass defaultType, TableColumn column);

    default JavaClass createJavaClass(JavaType javaType) {
        return new JavaClass(javaType.getSimpleName(), javaType.getClassName());
    }
}
