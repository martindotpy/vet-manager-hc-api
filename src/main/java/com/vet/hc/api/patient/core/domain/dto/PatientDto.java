package com.vet.hc.api.patient.core.domain.dto;

import java.time.LocalDate;
import java.util.List;

import com.vet.hc.api.client.core.domain.dto.ClientDto;
import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.raze.domain.dto.RazeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO class for Patient entity.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PatientDto {
    private Long id;

    private String name;
    private LocalDate birthDay;
    private Byte age;
    private String characteristics;
    private boolean deceased;

    private Genre genre;
    private ClientDto owner;
    private RazeDto raze;
    private List<MedicalHistoryDto> medicalHistories;
}
