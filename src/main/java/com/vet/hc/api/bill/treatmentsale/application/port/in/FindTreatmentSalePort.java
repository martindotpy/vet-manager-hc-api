package com.vet.hc.api.bill.treatmentsale.application.port.in;

import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the treatment sale.
 */
public interface FindTreatmentSalePort {
    /**
     * Find the treatment sale by id.
     *
     * @param id the treatment sale id.
     * @return the treatment sale found or the failure
     */
    Result<TreatmentSaleDto, TreatmentSaleFailure> findById(Long id);
}
