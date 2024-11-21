package com.vet.hc.api.appointment.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppointmentTypeFailure implements Failure {
    ;

    private final String message;
}
