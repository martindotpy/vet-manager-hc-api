package com.vet.hc.api.patient.core.adapter.in.request;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.core.domain.payload.UpdatePatientPayload;
import com.vet.hc.api.patient.medicalhistory.adapter.in.request.UpdateMedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.domain.payload.UpdateMedicalHistoryPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdatePatientDto implements UpdatePatientPayload {
    private Long id;

    private String name;
    private LocalDate birthDate;
    private String characteristics;
    private Genre genre;

    private Long ownerId;
    private Long razeId;
    private List<UpdateMedicalHistoryDto> medicalHistories;

    @Override
    public List<UpdateMedicalHistoryPayload> getMedicalHistories() {
        return medicalHistories.stream()
                .map(dto -> (UpdateMedicalHistoryPayload) dto)
                .collect(Collectors.toList());
    }
}
