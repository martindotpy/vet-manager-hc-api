package com.vluepixel.vetmanager.api.shared.application.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * String utils mapper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtilsMapper {
    public static String trimString(String value) {
        return value != null ? value.trim() : null;
    }
}
