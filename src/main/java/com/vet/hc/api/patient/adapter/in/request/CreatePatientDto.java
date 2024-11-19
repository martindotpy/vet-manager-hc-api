package com.vet.hc.api.patient.adapter.in.request;

import com.vet.hc.api.patient.domain.payload.CreatePatientPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreatePatientDto implements CreatePatientPayload {
    private String name;
}
