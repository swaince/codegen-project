package com.dfec.codegen.config;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
@Data
@Builder(builderClassName = "Builder")
public class PackageConfig {

    @Default
    private String basePackage = "com.dfec";
    private String module;
    @Default
    private String entity = "entity";
    @Default
    private String mapper = "mapper";
    @Default
    private String service = "service";
    @Default
    private String controller = "controller";
    @Default
    private String mapperXml = "mapper.xml";

    public String getModulePackage() {
        if (module == null || module.isEmpty()) {
            return basePackage;
        }
        return String.format("%s.%s", basePackage, module);
    }

    public String getCommonPackage(String subPackage, String defaultValue) {
        if (subPackage == null || subPackage.isEmpty()) {
            return String.format("%s.%s", getModulePackage(), defaultValue);
        }
        return String.format("%s.%s", getModulePackage(), subPackage);
    }

    public String getEntityPackage() {
        return getCommonPackage(entity, "entity");
    }

    public String getMapperPackage() {
        return getCommonPackage(mapper, "mapper");
    }

    public String getServicePackage() {
        return getCommonPackage(service, "service");
    }

    public String getServiceImplPackage() {
        return getCommonPackage(controller, "service.impl");
    }

    public String getControllerPackage() {
        return getCommonPackage(controller, "controller");
    }
}
