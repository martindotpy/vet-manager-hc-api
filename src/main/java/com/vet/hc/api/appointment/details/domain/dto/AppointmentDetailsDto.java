package com.vet.hc.api.appointment.details.domain.dto;

import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for appointment details.
 *
 * @see com.vet.hc.api.appointment.details.domain.model.AppointmentDetails
 */
@ColumnClassName("Detalles de la cita")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentDetailsDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Duración (minutos)")
    private Long durationInMinutes;
    @ColumnPropertyName("Precio")
    private Double price;

    @ColumnPropertyName("Tipo")
    private AppointmentTypeDto type;
}
