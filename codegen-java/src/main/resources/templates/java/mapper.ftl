package ${mapper.packageName};

<#list mapper.imports as pkg>
import ${pkg};
</#list>

/**
 * ${mapper.remark!} Mapper 接口
 *
 * @author ${mapper.author}
 * @since ${mapper.date}
 */
<#list mapper.annotations as an>
${an}
</#list>
public interface ${mapper.name} <#if mapper.useMybatisPlus>extends ${mapper.superClass}<${entity.name}></#if>{

}