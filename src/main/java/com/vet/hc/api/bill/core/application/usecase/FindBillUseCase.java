package com.vet.hc.api.bill.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.core.application.mapper.BillMapper;
import com.vet.hc.api.bill.core.application.port.in.FindBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.query.PaginatedBill;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find an bill .
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindBillUseCase implements FindBillPort {
    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Override
    public Result<PaginatedBill, BillFailure> match(Criteria criteria) {
        log.info("Finding bills by criteria: {}", criteria);

        var result = billRepository.match(criteria);

        if (result.isFailure()) {
            log.error("Error finding bills by criteria: {}", criteria);

            return Result.failure(BillFailure.UNEXPECTED);
        }

        log.info("Bills found by criteria: {}", result.getSuccess().getSize());

        return Result.success(PaginatedBill.builder()
                .content(result.getSuccess().getContent().stream()
                        .map(billMapper::toDto)
                        .toList())
                .page(result.getSuccess().getPage())
                .size(result.getSuccess().getSize())
                .totalElements(result.getSuccess().getTotalElements())
                .totalPages(result.getSuccess().getTotalPages())
                .build());
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
