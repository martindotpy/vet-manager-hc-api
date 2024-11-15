package com.vet.hc.api.patient.domain.response;

import com.vet.hc.api.patient.domain.dto.PatientDto;
import com.vet.hc.api.shared.domain.query.ContentResponse;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class PatientResponse extends ContentResponse<PatientDto> {
}
