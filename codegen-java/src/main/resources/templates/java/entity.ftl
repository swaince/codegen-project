package ${entity.packageName};

<#list entity.imports as ipkg>
import ${ipkg};
</#list>

/**
 * ${table.remark}
 *
 * @author: ${entity.author}
 * @date: ${entity.date}
 */
<#list entity.annotations as an>
${an}
</#list>
public class ${entity.name}<#if entity.superClass??> extends ${entity.superClass}<#if entity.generic><${entity.name}></#if></#if><#if entity.serializable> implements Serializable</#if> {

<#if entity.serializable>
    private static final long serialVersionUID = 1L;

</#if>
<#list entity.properties as property>
    <#if property.remark??>
    /**
     * ${property.remark}
     */
    </#if>
    <#list property.annotations as an>
    ${an}
    </#list>
    private ${property.type.name} ${property.name};

</#list>
<#if (!entity.useLombok)>
<#list entity.properties as property>
    public ${property.type.name} ${property.getterName}() {
        return ${property.name};
    }

    public void ${property.setterName}(${property.type.name} ${property.name}) {
        this.${property.name} = ${property.name};
    }
</#list>
</#if>
}