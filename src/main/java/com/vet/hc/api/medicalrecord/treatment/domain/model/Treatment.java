package com.vet.hc.api.medicalrecord.treatment.domain.model;

import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a treatment.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Treatment {
    private Long id;

    private Integer order;
    private String description;

    private MedicalRecord medicalRecord;
}
