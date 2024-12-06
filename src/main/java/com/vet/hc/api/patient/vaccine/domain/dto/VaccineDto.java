package com.vet.hc.api.patient.vaccine.domain.dto;

import java.time.LocalDateTime;

import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for vaccine.
 *
 * @see com.vet.hc.api.patient.vaccine.domain.model.Vaccine
 */
@ColumnClassName("Vacuna")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class VaccineDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Nombre")
    private String name;
    @ColumnPropertyName("Dosis")
    private Integer dose;
    @ColumnPropertyName("Fecha de vacunación")
    private LocalDateTime vaccinatedAt;

    @ColumnPropertyName("Veterinario")
    private UserDto vaccinator;
}
