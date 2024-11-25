package com.vet.hc.api.bill.treatmentsale.domain.model;

import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a treatment sale.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentSale {
    private Long id;

    private Double price;
    private Integer discount;

    private Treatment treatment;
    private User seller;
    @Nullable
    private Bill bill;
}
