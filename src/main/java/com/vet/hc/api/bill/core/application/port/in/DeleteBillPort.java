package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for deleting a bill.
 */
public interface DeleteBillPort {
    Result<Void, BillFailure> deleteById(Long id);
}
