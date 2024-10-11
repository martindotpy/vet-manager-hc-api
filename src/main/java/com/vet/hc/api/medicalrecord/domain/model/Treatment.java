package com.vet.hc.api.medicalrecord.domain.model;

import com.vet.hc.api.sales.domain.model.Bill;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a treatment.
 */
@Getter
@Builder
public class Treatment {
    private Long id;

    private Byte order;
    private String description;

    private MedicalRecord medicalRecord;
    private Bill bill;
}
