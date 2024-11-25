package com.vet.hc.api.bill.treatmentsale.domain.repository;

import java.util.Optional;

import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository interface for {@link TreatmentSale} model.
 */
public interface TreatmentSaleRepository {
    /**
     * Find a treatment sale by its id.
     *
     * @param id the treatment sale id.
     * @return an optional of treatment sale
     */
    Optional<TreatmentSale> findById(Long id);

    /**
     * Save a treatment sale.
     *
     * @param treatmentSale the treatment sale to save
     * @return Result with the saved treatment sale or a failure
     */
    Result<TreatmentSale, RepositoryFailure> save(TreatmentSale treatmentSale);

    /**
     * Delete a treatment sale by its id.
     *
     * @param id the treatment sale id
     * @return Result with success or a failure
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
