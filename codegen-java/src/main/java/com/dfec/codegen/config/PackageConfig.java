package com.dfec.codegen.config;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
@Data
@Builder
public class PackageConfig {

    @Builder.Default
    private String basePackage = "com.dfec";
    private String module;
    @Builder.Default
    private String entity = "entity";
    @Builder.Default
    private String mapper = "mapper";
    @Builder.Default
    private String service = "service";
    @Builder.Default
    private String controller = "controller";
    @Builder.Default
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
