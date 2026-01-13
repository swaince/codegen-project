package com.dfec.codegen.resolver;

import com.dfec.codegen.JavaGenerationModel;
import com.dfec.codegen.db.Table;

import java.nio.file.Paths;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public interface ModelResolver<T> {

    T resolve(JavaGenerationModel model, Table table);

    default String getOutputDir(String pkg, String base) {
        String[] dirs = pkg.split("\\.");
        String[] dirList = new String[dirs.length + 3];
        dirList[0] = "src";
        dirList[1] = "main";
        dirList[2] = "java";
        System.arraycopy(dirs, 0, dirList, 3, dirs.length);

        return Paths.get(base, dirList).toString();
    }
}
