package com.vet.hc.api.medicalrecord.domain.model;

import com.vet.hc.api.sales.domain.model.Bill;

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

    private Byte order;
    private String description;

    private MedicalRecord medicalRecord;
    private Bill bill;
}
