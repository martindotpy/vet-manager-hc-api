package com.vet.hc.api.patient.race.adapter.in.response;

import java.util.List;

import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link RaceDto} list.
 */
@SuperBuilder
public final class RacesResponse extends ContentResponse<List<RaceDto>> {
}
