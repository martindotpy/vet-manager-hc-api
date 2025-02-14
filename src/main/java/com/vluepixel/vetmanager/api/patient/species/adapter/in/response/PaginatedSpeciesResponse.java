package com.vluepixel.vetmanager.api.patient.species.adapter.in.response;

import com.vluepixel.vetmanager.api.patient.species.application.dto.SpeciesDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated species response.
 */
@SuperBuilder
public final class PaginatedSpeciesResponse extends PaginatedResponse<SpeciesDto> {
}
