package com.vet.hc.api.medicalrecord.core.adapter.in.request;

import java.time.LocalDateTime;

import com.vet.hc.api.medicalrecord.core.domain.payload.UpdateMedicalRecordPayload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to update a medical record.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateMedicalRecordRequest implements UpdateMedicalRecordPayload {
    @NotNull(message = "El id es requerido")
    @Min(value = 1, message = "El id no puede ser menor a 1")
    private Long id;

    @NotNull(message = "La fecha de ingreso es requerida")
    private LocalDateTime entryTime;
    @NotNull(message = "La razón es requerido")
    @NotEmpty(message = "La razón no puede estar vacío")
    @NotBlank(message = "La razón no puede estar en blanco")
    private String reason;
    @NotNull(message = "El examen físico es requerido")
    @NotEmpty(message = "El examen físico no puede estar vacío")
    @NotBlank(message = "El examen físico no puede estar en blanco")
    private String physicalExamination;
    @NotNull(message = "La temperatura en grados Celsius es requerida")
    @DecimalMin(value = "0.0", message = "La temperatura en grados Celsius no puede ser menor a 0.0")
    private Float celsiusTemperature;
    @NotNull(message = "La frecuencia respiratoria es requerida")
    @DecimalMin(value = "0.0", message = "La frecuencia respiratoria no puede ser menor a 0.0")
    private Float respiratoryRate;
    @NotNull(message = "La frecuencia cardíaca es requerida")
    @DecimalMin(value = "0.0", message = "La frecuencia cardíaca no puede ser menor a 0.0")
    private Float heartRate;
    @NotNull(message = "El peso es requerido")
    @DecimalMin(value = "0.0", message = "El peso no puede ser menor a 0.0")
    private Float weight;
    @NotNull(message = "La esterilización es requerida")
    private boolean sterilized;
    @NotNull(message = "El examen complementario es requerido")
    @NotEmpty(message = "El examen complementario no puede estar vacío")
    @NotBlank(message = "El examen complementario no puede estar en blanco")
    private String supplementaryExamination;
    @NotNull(message = "La receta es requerida")
    @NotEmpty(message = "La receta no puede estar vacía")
    @NotBlank(message = "La receta no puede estar en blanco")
    private String recipe;
    @NotNull(message = "El diagnóstico es requerido")
    @NotEmpty(message = "El diagnóstico no puede estar vacío")
    @NotBlank(message = "El diagnóstico no puede estar en blanco")
    private String diagnosis;

    @NotNull(message = "El id del veterinario es requerido")
    @Min(value = 1, message = "El id del veterinario no puede ser menor a 1")
    private Long vetId;
    @NotNull(message = "El id del paciente es requerido")
    @Min(value = 1, message = "El id del paciente no puede ser menor a 1")
    private Long patientId;
}
