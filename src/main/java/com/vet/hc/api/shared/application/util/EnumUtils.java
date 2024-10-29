package com.vet.hc.api.shared.application.util;

import java.util.Arrays;

public class EnumUtils {
    public static String[] getEnumNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toArray(String[]::new);
    }
}
