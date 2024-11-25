package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.query.PaginatedBill;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding a bill.
 */
public interface FindBillPort {
    /**
     * Find a bill by its id.
     *
     * @param id The id of the bill.
     * @return The bill if found, otherwise a failure.
     */
    Result<BillDto, BillFailure> findById(Long id);

    /**
     * Find all matching bills.
     *
     * @param criteria The criteria to filter the bills.
     * @return The matching bills
     */
    Result<PaginatedBill, BillFailure> match(Criteria criteria);
}
