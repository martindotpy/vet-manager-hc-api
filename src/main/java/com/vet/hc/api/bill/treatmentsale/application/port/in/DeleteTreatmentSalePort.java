package com.vet.hc.api.bill.treatmentsale.application.port.in;

import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a treatment sale by id.
 */
public interface DeleteTreatmentSalePort {
    /**
     * Delete a treatment sale by id.
     *
     * @param id Id of the treatment sale to delete.
     * @return Result with the success or the failure
     */
    Result<Void, TreatmentSaleFailure> deleteById(Long id);
}
