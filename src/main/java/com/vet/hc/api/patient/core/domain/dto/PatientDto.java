package com.vet.hc.api.patient.core.domain.dto;

import java.time.LocalDate;
import java.util.List;

import com.vet.hc.api.client.core.domain.dto.ClientDto;
import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.race.domain.dto.RaceDto;
import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the patient.
 */
@ColumnClassName("Paciente")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PatientDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Nombre")
    private String name;
    @ColumnPropertyName("Fecha de nacimiento")
    private LocalDate birthDate;
    @ColumnPropertyName("Edad")
    private Integer age;
    @ColumnPropertyName("Características")
    private String characteristics;
    @ColumnPropertyName("Fallecido")
    private boolean deceased;

    @ColumnPropertyName("Género")
    private Genre genre;
    @ColumnPropertyName("Dueño")
    private ClientDto owner;
    @ColumnPropertyName("Raza")
    private RaceDto race;
    @ColumnPropertyName("Vacunas")
    private List<VaccineDto> vaccines;
    @ColumnPropertyName("Historial médico")
    private List<MedicalHistoryDto> medicalHistories;
    @ColumnPropertyName("Historial clínico")
    private List<MedicalRecordDto> medicalRecords;
}
