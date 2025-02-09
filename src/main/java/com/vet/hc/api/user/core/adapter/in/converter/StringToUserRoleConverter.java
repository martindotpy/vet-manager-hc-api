package com.vet.hc.api.user.core.adapter.in.converter;

import org.springframework.stereotype.Component;

import com.vet.hc.api.shared.adapter.in.converter.StringToEnumConverter;
import com.vet.hc.api.user.core.domain.model.enums.UserRole;

/**
 * Converter class for converting a string to a user role.
 */
@Component
public class StringToUserRoleConverter extends StringToEnumConverter<UserRole> {
    public StringToUserRoleConverter() {
        super(UserRole.class);
    }
}
