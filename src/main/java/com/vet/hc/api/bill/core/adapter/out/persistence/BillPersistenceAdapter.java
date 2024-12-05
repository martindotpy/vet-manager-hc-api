package com.vet.hc.api.bill.core.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.bill.core.adapter.out.persistence.repository.BillHibernateRepository;
import com.vet.hc.api.bill.core.application.mapper.BillMapper;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for bill persistence.
 */
@Slf4j
@NoArgsConstructor
public class BillPersistenceAdapter implements BillRepository {
    private BillHibernateRepository billHibernateRepository;

    private BillMapper billMapper = BillMapper.INSTANCE;

    @Inject
    public BillPersistenceAdapter(BillHibernateRepository billHibernateRepository) {
        this.billHibernateRepository = billHibernateRepository;
    }

    @Override
    public List<Bill> findAll() {
        return billHibernateRepository.findAll().stream()
                .map(billMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return billHibernateRepository.findById(id)
                .map(billMapper::toDomain);
    }

    @Override
    public Result<Paginated<Bill>, RepositoryFailure> match(Criteria criteria) {
        try {
            var response = billHibernateRepository.match(criteria);

            return Result.success(
                    Paginated.<Bill>builder()
                            .content(response.getContent().stream()
                                    .map(billMapper::toDomain)
                                    .toList())
                            .page(response.getPage())
                            .size(response.getSize())
                            .totalElements(response.getTotalElements())
                            .totalPages(response.getTotalPages())
                            .build());
        } catch (IllegalArgumentException e) {
            log.warn("Field not found in criteria: {}", e.getMessage());
            return Result.failure(RepositoryFailure.FIELD_NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Bill, RepositoryFailure> save(Bill bill) {
        try {
            return Result.success(billMapper
                    .toDomain(billHibernateRepository.save(billMapper.toEntity(bill))));
        } catch (Exception e) {
            log.error("Error saving bill with creator id `{}`", bill.getCreatedBy().getId(), e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            billHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Bill with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting bill with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }
}
