package com.vluepixel.vetmanager.api.patient.race.adapter.in.response;

import com.vluepixel.vetmanager.api.patient.race.application.dto.RaceDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated race response.
 */
@SuperBuilder
public final class PaginatedRaceResponse extends PaginatedResponse<RaceDto> {
}
