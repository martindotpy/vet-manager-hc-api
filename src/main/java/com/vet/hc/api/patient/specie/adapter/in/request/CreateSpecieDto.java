package com.vet.hc.api.patient.specie.adapter.in.request;

import com.vet.hc.api.patient.specie.domain.payload.CreateSpeciePayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents the request payload to create a new specie.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateSpecieDto implements CreateSpeciePayload {
    private String name;
}
