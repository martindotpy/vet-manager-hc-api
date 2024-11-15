package com.vet.hc.api.patient.domain.response;

import com.vet.hc.api.patient.domain.dto.PatientDto;
import com.vet.hc.api.shared.domain.query.PaginatedResponse;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class PaginatedPatientResponse extends PaginatedResponse<PatientDto> {
}
