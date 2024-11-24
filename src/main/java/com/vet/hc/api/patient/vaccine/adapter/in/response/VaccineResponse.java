package com.vet.hc.api.patient.vaccine.adapter.in.response;

import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link VaccineDto}.
 */
@SuperBuilder
public final class VaccineResponse extends ContentResponse<VaccineDto> {
}
