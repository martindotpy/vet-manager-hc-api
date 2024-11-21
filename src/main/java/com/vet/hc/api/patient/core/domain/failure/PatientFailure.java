package com.vet.hc.api.patient.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PatientFailure implements Failure {
    ;

    private final String message;
}
