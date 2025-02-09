package com.vet.hc.api.user.core.adapter.out.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.vet.hc.api.shared.adapter.out.persistence.converter.ListEnumAttributeConverter;
import com.vet.hc.api.user.core.domain.model.enums.UserRole;

import jakarta.persistence.Converter;

/**
 * List user role attribute converter.
 */
@Converter
public class ListUserRoleAttributeConverter implements ListEnumAttributeConverter<UserRole> {
    @Override
    public String convertToDatabaseColumn(List<UserRole> attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.stream()
                .map(UserRole::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<UserRole> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return List.of();
        }

        return List.of(dbData.split(","))
                .stream()
                .map(UserRole::valueOf)
                .toList();
    }
}
