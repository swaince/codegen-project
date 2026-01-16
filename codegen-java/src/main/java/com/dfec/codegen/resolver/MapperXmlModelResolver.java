package com.dfec.codegen.resolver;

import com.dfec.codegen.JavaGenerationModel;
import com.dfec.codegen.config.JavaGenerationConfig;
import com.dfec.codegen.config.StrategyConfig;
import com.dfec.codegen.db.Table;
import com.dfec.codegen.model.MapperModel;
import com.dfec.codegen.model.MapperXmlModel;

import java.nio.file.Paths;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public class MapperXmlModelResolver implements ModelResolver<MapperXmlModel> {
    @Override
    public MapperXmlModel resolve(JavaGenerationModel model, Table table) {
        JavaGenerationConfig config = model.getMetadata().getConfig();
        String base = config.getBase();
        MapperXmlModel mapperXml = new MapperXmlModel();
        MapperModel mapper = model.getMapper();
        String xmlPath = Paths.get(base, "src", "main", "resources").resolve("mappers").resolve(mapper.getName() + ".xml").toString();
        mapperXml.setOutputDir(xmlPath);
        return mapperXml;
    }
}
