package com.vet.hc.api.medicalrecord.core.adapter.in.status;

import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for medical record failures.
 */
public final class HttpStatusCodeMedicalRecordFailureHandler
        implements HttpStatusCodeFailureHandler<MedicalRecordFailure> {
    @Override
    public int getHttpStatusCode(MedicalRecordFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
