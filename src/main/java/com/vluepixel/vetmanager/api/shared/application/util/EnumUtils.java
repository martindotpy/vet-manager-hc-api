package com.vluepixel.vetmanager.api.shared.application.util;

import java.util.Arrays;
import java.util.function.Function;

public class EnumUtils {
    /**
     * Returns the names of the enum values in uppercase by default.
     *
     * @param e the enum class
     * @return the names of the enum values in uppercase
     */
    public static String[] getEnumNames(Class<? extends Enum<?>> e) {
        return getEnumNames(e, String::toUpperCase);
    }

    /**
     * Returns the transformed names of the enum values based on the provided
     * transformer.
     *
     * @param e               the enum class
     * @param nameTransformer a function to transform the enum names
     * @return the transformed names of the enum values
     */
    public static String[] getEnumNames(Class<? extends Enum<?>> e, Function<String, String> nameTransformer) {
        return Arrays.stream(e.getEnumConstants())
                .map(Enum::name)
                .map(nameTransformer)
                .toArray(String[]::new);
    }
}
