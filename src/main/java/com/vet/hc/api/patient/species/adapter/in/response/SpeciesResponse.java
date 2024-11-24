package com.vet.hc.api.patient.species.adapter.in.response;

import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link SpeciesDto}.
 */
@SuperBuilder
public final class SpeciesResponse extends ContentResponse<SpeciesDto> {
}
