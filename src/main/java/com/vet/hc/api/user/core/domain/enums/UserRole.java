package com.vet.hc.api.user.core.domain.enums;

import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

/**
 * The roles of the user in the system.
 */
public enum UserRole {
    @ColumnPropertyName("Administrador")
    ADMIN,
    @ColumnPropertyName("Usuario")
    USER,
    @ColumnPropertyName("Veterinario")
    VET;
}
