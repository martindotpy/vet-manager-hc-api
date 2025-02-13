package com.vluepixel.vetmanager.api.user.core.adapter.in.converter;

import org.springframework.stereotype.Component;

import com.vluepixel.vetmanager.api.shared.adapter.in.converter.StringToEnumConverter;
import com.vluepixel.vetmanager.api.user.core.domain.model.enums.UserRole;

/**
 * Converter class for converting a string to a user role.
 */
@Component
public class StringToUserRoleConverter extends StringToEnumConverter<UserRole> {
    public StringToUserRoleConverter() {
        super(UserRole.class);
    }
}
