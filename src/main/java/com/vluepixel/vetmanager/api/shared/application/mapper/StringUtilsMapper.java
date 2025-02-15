package com.vluepixel.vetmanager.api.shared.application.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * String utils mapper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtilsMapper {
    /**
     * Trims the given string.
     *
     * @param value the value.
     * @return the trimmed string
     */
    public static String trimString(String value) {
        return value != null ? value.trim() : null;
    }
}
