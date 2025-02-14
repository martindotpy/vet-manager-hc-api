package com.vluepixel.vetmanager.api.patient.core.adapter.in.response;

import com.vluepixel.vetmanager.api.patient.core.application.dto.PatientDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated patient response.
 */
@SuperBuilder
public final class PaginatedPatientResponse extends PaginatedResponse<PatientDto> {
}
