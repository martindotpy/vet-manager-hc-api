package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting an appointment type by id.
 */
public interface DeleteBillPort {
    /**
     * Delete an appointment type by id.
     *
     * @param id Id of the appointment type to delete.
     * @return Result with the success or the failure
     */
    Result<Void, BillFailure> deleteById(Long id);
}
