package com.vet.hc.api.patient.specie.adapter.in.request;

import com.vet.hc.api.patient.specie.domain.payload.UpdateSpeciePayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateSpecieDto implements UpdateSpeciePayload {
    private Long id;

    private String name;
}
