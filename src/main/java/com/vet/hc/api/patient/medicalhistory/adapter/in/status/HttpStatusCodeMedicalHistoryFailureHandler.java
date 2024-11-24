package com.vet.hc.api.patient.medicalhistory.adapter.in.status;

import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureHandler;

/**
 * Handler for HTTP status codes for medical history failures.
 */
public final class HttpStatusCodeMedicalHistoryFailureHandler
        implements HttpStatusCodeFailureHandler<MedicalHistoryFailure> {
    @Override
    public int getHttpStatusCode(MedicalHistoryFailure failure) {
        return switch (failure) {
            case NOT_FOUND -> 404;
            case UNEXPECTED -> 500;
        };
    }
}
