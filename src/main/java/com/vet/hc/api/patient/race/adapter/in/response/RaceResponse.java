package com.vet.hc.api.patient.race.adapter.in.response;

import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link RaceDto}.
 */
@SuperBuilder
public final class RaceResponse extends ContentResponse<RaceDto> {
}
