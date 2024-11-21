package com.vet.hc.api.medicalrecord.treatment.domain.model;

import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.sales.bill.domain.model.Bill;

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
