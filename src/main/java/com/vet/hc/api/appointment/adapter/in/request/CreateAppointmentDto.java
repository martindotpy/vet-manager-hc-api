package com.vet.hc.api.appointment.adapter.in.request;

import java.util.List;

import com.vet.hc.api.appointment.domain.enums.AppointmentType;
import com.vet.hc.api.appointment.domain.payload.CreateAppointmentPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Create appointment dto (request).
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateAppointmentDto implements CreateAppointmentPayload {
    private Long id;
    private String description;
    private List<AppointmentType> types;
    private Long patientId;
}
