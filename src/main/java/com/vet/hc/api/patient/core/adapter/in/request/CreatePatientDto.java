package com.vet.hc.api.patient.core.adapter.in.request;

import java.time.LocalDate;

import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.core.domain.payload.CreatePatientPayload;

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
    private LocalDate birthDate;
    private String characteristics;
    private Genre genre;
    private Long ownerId;
    private Long razeId;
    private String medicalHistoryContent;
}
