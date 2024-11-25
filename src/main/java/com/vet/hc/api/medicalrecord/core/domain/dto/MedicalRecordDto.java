package com.vet.hc.api.medicalrecord.core.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for medical record.
 *
 * @see com.vet.hc.api.patient.medicalrecord.domain.model.MedicalRecord
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicalRecordDto {
    private Long id;

    private LocalDateTime entryTime;
    private String reason;
    private String physicalExamination;
    private Float celsiusTemperature;
    private Float respiratoryRate;
    private Float heartRate;
    private Float weight;
    private boolean sterilized;
    private String supplementaryExamination;
    private String recipe;
    private String diagnosis;

    private UserDto vet;
    private List<TreatmentDto> treatments;
}
