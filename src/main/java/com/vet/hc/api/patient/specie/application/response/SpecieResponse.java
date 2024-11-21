package com.vet.hc.api.patient.specie.application.response;

import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class SpecieResponse extends ContentResponse<SpecieDto> {
}
