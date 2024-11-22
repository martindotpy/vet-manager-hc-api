package com.vet.hc.api.bill.core.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository interface for {@link Bill} model.
 */
public interface BillRepository {
    /**
     * Find all appointment types.
     *
     * @return a list of appointment types
     */
    List<Bill> findAll();

    /**
     * Find an appointment type by its id.
     *
     * @param id the appointment type id.
     * @return an optional of appointment type
     */
    Optional<Bill> findById(Long id);

    /**
     * Save an appointment type.
     *
     * @param bill the appointment type to save
     * @return Result with the saved appointment type or a failure
     */
    Result<Bill, RepositoryFailure> save(Bill bill);

    /**
     * Delete an appointment type by its id.
     *
     * @param id the appointment type id
     * @return Result with success or a failure
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
