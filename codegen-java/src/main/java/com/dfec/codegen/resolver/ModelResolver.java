package com.dfec.codegen.resolver;

import com.dfec.codegen.JavaGenerationModel;
import com.dfec.codegen.Language;
import com.dfec.codegen.db.Table;

import java.nio.file.Paths;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public interface ModelResolver<T> {

    T resolve(JavaGenerationModel model, Table table);

    default String getOutputDir(String base, String pkg, Language language, String filename) {
        String[] dirs = pkg.split("\\.");
        return Paths.get(base, "src").resolve("main").resolve(Paths.get(language.getName(), dirs)).resolve(filename + language.getExtension()).toString();
    }
}
