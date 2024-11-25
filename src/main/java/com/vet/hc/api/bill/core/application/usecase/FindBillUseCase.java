package com.vet.hc.api.bill.core.application.usecase;

import com.vet.hc.api.bill.core.application.mapper.BillMapper;
import com.vet.hc.api.bill.core.application.port.in.FindBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.query.PaginatedBill;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find an bill .
 */
@Slf4j
@NoArgsConstructor
public final class FindBillUseCase implements FindBillPort {
    private BillRepository billRepository;

    private final BillMapper billMapper = BillMapper.INSTANCE;

    @Inject
    public FindBillUseCase(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Result<PaginatedBill, BillFailure> match(Criteria criteria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'match'");
    }

    @Override
    public Result<BillDto, BillFailure> findById(Long id) {
        log.info("Finding bill by id: {}", id);

        var result = billRepository.findById(id);

        if (result.isEmpty()) {
            log.error("Bill not found with id: {}", id);

            return Result.failure(BillFailure.NOT_FOUND);
        }

        log.info("Bill found: {}", result.get());

        return Result.success(billMapper.toDto(result.get()));
    }
}
