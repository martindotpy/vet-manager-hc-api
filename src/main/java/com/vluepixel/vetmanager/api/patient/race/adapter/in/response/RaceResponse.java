package com.vluepixel.vetmanager.api.patient.race.adapter.in.response;

import com.vluepixel.vetmanager.api.patient.race.application.dto.RaceDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Race response.
 */
@SuperBuilder
public final class RaceResponse extends ContentResponse<RaceDto> {
}
