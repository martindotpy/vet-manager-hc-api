package com.vluepixel.vetmanager.api.shared.domain.util;

import java.lang.reflect.Field;

import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Spanish utilities.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpanishUtils {
    /**
     * Get the name of a class in Spanish.
     *
     * @param clazz The class.
     * @return The name of the class in Spanish. If the class does not have a
     *         Spanish name, the simple name of the class is returned.
     */
    public static String getName(Class<?> clazz) {
        return clazz.getAnnotation(SpanishName.class) == null
                ? clazz.getSimpleName()
                : clazz.getAnnotation(SpanishName.class).value();
    }

    public static String getName(Class<?> clazz, String field) {
        try {
            Field f = clazz.getDeclaredField(field);
            return f.getAnnotation(SpanishName.class) == null
                    ? f.getName()
                    : f.getAnnotation(SpanishName.class).value();
        } catch (NoSuchFieldException e) {
            return field;
        }
    }
}
