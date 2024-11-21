package com.vet.hc.api.patient.specie.application.response;

import java.util.List;

import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class SpeciesResponse extends ContentResponse<List<SpecieDto>> {
}
