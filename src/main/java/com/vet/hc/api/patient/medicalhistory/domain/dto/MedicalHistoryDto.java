package com.vet.hc.api.patient.medicalhistory.domain.dto;

import java.time.LocalDateTime;

import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for medical history.
 *
 * @see com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory
 */
@ColumnClassName("Historial médico")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicalHistoryDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Contenido")
    private String content;
    @ColumnPropertyName("Fecha de creación")
    private LocalDateTime createdAt;
}
