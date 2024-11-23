package com.vet.hc.api.appointment.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for appointment operations.
 */
@Getter
@AllArgsConstructor
public enum AppointmentFailure implements Failure {
    NOT_FOUND("Appointment not found"),
    FIELD_NOT_FOUND("Field not found"),
    UNEXPECTED("Unexpected error");

    private final String message;
}
