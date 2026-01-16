<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapper.packageName}.${mapper.name}">
    <resultMap id="BaseResultMap" type="${entity.packageName}.${entity.name}">
    <#list entity.properties as property>
        <#if property.column.primaryKey>
        <id column="${property.column.name}" jdbcType="${property.jdbcType}" property="${property.name}" />
        <#else >
        <result column="${property.column.name}" jdbcType="${property.jdbcType}" property="${property.name}" />
        </#if>
    </#list>
    </resultMap>
</mapper>