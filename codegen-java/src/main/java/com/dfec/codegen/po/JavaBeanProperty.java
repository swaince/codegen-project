package com.dfec.codegen.po;

import com.dfec.codegen.attributes.AnnotationAttribute;
import com.dfec.codegen.db.TableColumn;
import com.dfec.codegen.types.JavaClass;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author: zhangth
 * @date: 2026/1/12 15:11
 */
@Data
public class JavaBeanProperty {

    private String name;

    private JavaClass type;

    private String remark;

    /**
     * 注解属性
     */
    private List<AnnotationAttribute> annotationAttributes;

    /**
     * 注解
     */
    private Set<String> annotations;

    /**
     * 列信息
     */
    private TableColumn column;

    /**
     * getter 方法名
     */
    private String getterName;

    /**
     * setter 方法名
     */
    private String setterName;

    /**
     * jdbcType
     */
    private String jdbcType;
}
