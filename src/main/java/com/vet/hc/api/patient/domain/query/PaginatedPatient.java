package com.vet.hc.api.patient.domain.query;

import com.vet.hc.api.patient.domain.dto.PatientDto;
import com.vet.hc.api.shared.domain.query.Paginated;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class PaginatedPatient extends Paginated<PatientDto> {
}
