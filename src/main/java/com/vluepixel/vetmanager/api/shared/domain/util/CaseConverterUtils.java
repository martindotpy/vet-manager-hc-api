package com.vluepixel.vetmanager.api.shared.domain.util;

/**
 * Case converter util.
 */
public final class CaseConverterUtils {
    private CaseConverterUtils() {
    }

    /**
     * Convert to camel case.
     *
     * @param field field.
     * @return camel case. `null` if field is `null`.
     */
    public static String toCamelCase(String field) {
        if (field == null) {
            return null;
        }

        String[] parts = field.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0]);

        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i + 1];
            camelCaseString.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
        }

        return camelCaseString.toString();
    }

    /**
     * Convert to snake case.
     *
     * @param field field.
     * @return snake case. `null` if field is `null`.
     */
    public static String toSnakeCase(String field) {
        if (field == null) {
            return null;
        }

        return field.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
