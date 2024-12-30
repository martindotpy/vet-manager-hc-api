package com.vet.hc.api.user.core.domain.dto;

import java.util.Set;

import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;
import com.vet.hc.api.user.core.domain.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User data transfer object.
 */
@ColumnClassName("Usuario")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Nombre")
    private String firstName;
    @ColumnPropertyName("Apellido")
    private String lastName;
    @ColumnPropertyName("Correo electrónico")
    private String email;
    @ColumnPropertyName("Roles")
    private Set<UserRole> roles;
}
