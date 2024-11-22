package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the appointment type.
 */
public interface FindBillPort {
    /**
     * Find bills that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @return The list of bills that match the criteria
     */
    Result<Paginated<BillDto>, BillFailure> match(Criteria criteria);

    /**
     * Find the appointment type by id.
     *
     * @param id the appointment type id.
     * @return the appointment type found or the failure
     */
    Result<BillDto, BillFailure> findById(Long id);
}
