package com.vet.hc.api.appointment.core.adapter.in.request;

import java.util.List;

import com.vet.hc.api.appointment.core.domain.payload.UpdateAppointmentPayload;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update appointment dto (request).
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateAppointmentDto implements UpdateAppointmentPayload {
    private Long id;
    private String description;
    private List<AppointmentType> types;
    private Long patientId;
}
