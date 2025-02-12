package com.vluepixel.vetmanager.api.shared.application.util;

/**
 * Utility class for string operations.
 */
public class StringUtils {
    /**
     * Converts a snake case string to camel case.
     *
     * @param snakeCase The snake case string to convert.
     * @return The camel case string
     */
    public static String toCamelCase(String snakeCase) {
        StringBuilder camelCase = new StringBuilder();

        String[] words = snakeCase.split("_");

        camelCase.append(words[0].toLowerCase());

        for (int i = 1; i < words.length; i++) {
            camelCase.append(capitalize(words[i]));
        }

        return camelCase.toString();
    }

    /**
     * Capitalizes a word.
     *
     * @param word The word to capitalize.
     * @return The capitalized word.
     */
    private static String capitalize(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
