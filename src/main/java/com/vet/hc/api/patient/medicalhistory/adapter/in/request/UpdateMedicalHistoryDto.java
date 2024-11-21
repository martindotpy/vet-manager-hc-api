package com.vet.hc.api.patient.medicalhistory.adapter.in.request;

import com.vet.hc.api.patient.medicalhistory.domain.payload.UpdateMedicalHistoryPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMedicalHistoryDto implements UpdateMedicalHistoryPayload {
    private Long id;
    private String content;
}
