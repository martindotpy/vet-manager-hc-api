package com.vet.hc.api.patient.core.application.response;

import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class PaginatedPatientResponse extends PaginatedResponse<PatientDto> {
}
