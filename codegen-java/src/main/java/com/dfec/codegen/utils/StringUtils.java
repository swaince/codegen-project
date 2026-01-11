package com.dfec.codegen.utils;

import java.util.Set;

/**
 *
 * @author zhangth
 * @since 2026/1/11
 */
public class StringUtils {

    public static String trimPrefix(String string, Set<String> prefixes) {
        if (prefixes == null || prefixes.isEmpty()) {
            return string;
        }
        for (String prefix : prefixes) {
            if (string.startsWith(prefix)) {
                return string.substring(prefix.length());
            }
        }
        return string;
    }

    public static String trimSuffix(String string, Set<String> suffixes) {
        if (suffixes == null || suffixes.isEmpty()) {
            return string;
        }
        for (String suffix : suffixes) {
            if (string.endsWith(suffix)) {
                return string.substring(0, string.length() - suffix.length());
            }
        }
        return string;
    }
}
