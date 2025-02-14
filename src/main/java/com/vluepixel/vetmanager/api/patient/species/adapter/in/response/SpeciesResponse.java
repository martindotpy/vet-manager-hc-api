package com.vluepixel.vetmanager.api.patient.species.adapter.in.response;

import com.vluepixel.vetmanager.api.patient.species.application.dto.SpeciesDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Species response.
 */
@SuperBuilder
public final class SpeciesResponse extends ContentResponse<SpeciesDto> {
}
