package com.vluepixel.vetmanager.api.patient.core.application.dto;

import java.time.LocalDate;
import java.util.List;

import com.vluepixel.vetmanager.api.client.core.application.dto.ClientDto;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;
import com.vluepixel.vetmanager.api.patient.core.domain.enums.PatientGender;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.patient.race.application.dto.RaceDto;
import com.vluepixel.vetmanager.api.vaccine.core.application.dto.VaccineDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Patient DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PatientDto {
    private Long id;

    private String name;
    private LocalDate birthDate;
    private Integer age;
    private PatientGender gender;
    private String characteristics;
    private boolean deceased;
    private List<MedicalHistoryDto> medicalHistories;
    private List<MedicalRecordDto> medicalRecords;
    private List<VaccineDto> vaccines;

    private RaceDto race;
    private ClientDto owner;
}
