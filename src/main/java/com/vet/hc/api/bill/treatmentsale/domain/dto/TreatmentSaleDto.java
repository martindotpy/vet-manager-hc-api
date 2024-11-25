package com.vet.hc.api.bill.treatmentsale.domain.dto;

import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for treatment sale.
 *
 * @see com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TreatmentSaleDto {
    private Long id;

    private Double price;
    private Integer discount;

    private TreatmentDto treatment;
    private UserDto seller;
}
