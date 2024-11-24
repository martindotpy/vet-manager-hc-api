package com.vet.hc.api.patient.species.adapter.in.response;

import java.util.List;

import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link SpeciesDto} list.
 */
@SuperBuilder
public final class DifferentSpeciesResponse extends ContentResponse<List<SpeciesDto>> {
}
