package com.vet.hc.api.medicalrecord.core.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;
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
@ColumnClassName("Historial médico")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicalRecordDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Fecha de creación")
    private LocalDateTime entryTime;
    @ColumnPropertyName("Motivo")
    private String reason;
    @ColumnPropertyName("Examen físico")
    private String physicalExamination;
    @ColumnPropertyName("Temperatura (°C)")
    private Float celsiusTemperature;
    @ColumnPropertyName("Frecuencia respiratoria")
    private Float respiratoryRate;
    @ColumnPropertyName("Frecuencia cardíaca")
    private Float heartRate;
    @ColumnPropertyName("Peso")
    private Float weight;
    @ColumnPropertyName("Está esterilizado")
    private boolean sterilized;
    @ColumnPropertyName("Examen complementario")
    private String supplementaryExamination;
    @ColumnPropertyName("Diagnóstico")
    private String recipe;
    @ColumnPropertyName("Diagnóstico")
    private String diagnosis;

    @ColumnPropertyName("Veterinario")
    private UserDto vet;
    @ColumnPropertyName("Tratamientos")
    private List<TreatmentDto> treatments;
}
