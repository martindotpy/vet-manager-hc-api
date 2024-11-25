package com.vet.hc.api.patient.core.domain.dto;

import java.time.LocalDate;
import java.util.List;

import com.vet.hc.api.client.core.domain.dto.ClientDto;
import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PatientDto {
    private Long id;

    private String name;
    private LocalDate birthDate;
    private Integer age;
    private String characteristics;
    private boolean deceased;

    private Genre genre;
    private ClientDto owner;
    private RaceDto race;
    private List<VaccineDto> vaccines;
    private List<MedicalHistoryDto> medicalHistories;
    private List<MedicalRecordDto> medicalRecords;
}
