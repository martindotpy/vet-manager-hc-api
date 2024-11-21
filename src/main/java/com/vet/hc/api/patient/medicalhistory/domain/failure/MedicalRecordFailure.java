package com.vet.hc.api.patient.medicalhistory.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MedicalRecordFailure implements Failure {
    ;

    private final String message;
}
