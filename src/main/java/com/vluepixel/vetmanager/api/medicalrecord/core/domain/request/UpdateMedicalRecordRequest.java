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
    @NotNull(message = "El id es requerido")
    @Positive(message = "El id debe ser mayor a 0")
    private Long id;

    @NotBlank(message = "La razón es requerida")
    private String reason;
    @NotNull(message = "La fecha de ingreso es requerida")
    private LocalDateTime entryAt;
    private String physicalExam;
    @NotNull(message = "La temperatura es requerida")
    @Positive(message = "La temperatura debe ser mayor a 0")
    @DecimalMax(value = "100.0", message = "La temperatura no puede ser mayor a 100")
    private BigDecimal temperatureInCelsius;
    @NotNull(message = "La frecuencia respiratoria es requerida")
    @Positive(message = "La frecuencia respiratoria debe ser mayor a 0")
    private Integer respitarionRate;
    @NotNull(message = "La frecuencia cardiaca es requerida")
    @Positive(message = "La frecuencia cardiaca debe ser mayor a 0")
    @Max(value = 1000, message = "La frecuencia cardiaca no puede ser mayor a 1000")
    private Integer heartRate;
    @NotNull(message = "El peso es requerido")
    @Positive(message = "El peso debe ser mayor a 0")
    @DecimalMax(value = "999.99", message = "El peso no puede ser mayor a 999.99")
    private BigDecimal weight;
    private boolean sterilized;
    private String recipe;
    private String diagnosis;

    @NotNull(message = "El id del veterinario es requerido")
    @Positive(message = "El id del veterinario debe ser mayor a 0")
    private Long vetId;
}
