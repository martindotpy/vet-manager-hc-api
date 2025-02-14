package com.vluepixel.vetmanager.api.medicalrecord.core.domain.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update medical record request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateMedicalRecordRequest implements Request {
    @NotNull(message = "El id no puede estar vacío")
    @Positive(message = "El id debe ser un número positivo")
    private Long id;

    @NotBlank(message = "La razón no puede estar vacía")
    private String reason;
    @NotNull(message = "La fecha de ingreso no puede ser nula")
    private LocalDateTime entryAt;
    private String physicalExam;
    @NotNull(message = "La temperatura no puede ser nula")
    @Positive(message = "La temperatura debe ser mayor a 0")
    @DecimalMax(value = "100.0", message = "La temperatura no puede ser mayor a 100")
    private BigDecimal temperatureInCelsius;
    @NotNull(message = "La frecuencia respiratoria no puede ser nula")
    @Positive(message = "La frecuencia respiratoria debe ser mayor a 0")
    private Integer respitarionRate;
    @NotNull(message = "La frecuencia cardiaca no puede ser nula")
    @Positive(message = "La frecuencia cardiaca debe ser mayor a 0")
    @Max(value = 1000, message = "La frecuencia cardiaca no puede ser mayor a 1000")
    private Integer heartRate;
    @NotNull(message = "El peso no puede ser nulo")
    @Positive(message = "El peso debe ser mayor a 0")
    @DecimalMax(value = "999.99", message = "El peso no puede ser mayor a 999.99")
    private BigDecimal weight;
    private boolean sterilized;
    private String recipe;
    private String diagnosis;

    @NotNull(message = "El id del veterinario no puede ser nulo")
    @Positive(message = "El id del veterinario debe ser mayor a 0")
    private Long vetId;
}
