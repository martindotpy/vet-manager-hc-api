package com.vet.hc.api.appointment.type.domain.dto;

import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for appointment type.
 *
 * @see com.vet.hc.api.appointment.type.domain.model.AppointmentType
 */
@ColumnClassName("Tipo de cita")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentTypeDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Nombre")
    private String name;
    @ColumnPropertyName("Duración (minutos)")
    private Long durationInMinutes;
    @ColumnPropertyName("Precio")
    private Double price;
}
