package com.vet.hc.api.bill.appointmentsale.domain.repository;

import java.util.Optional;

import com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository interface for {@link AppointmentSale} model.
 */
public interface AppointmentSaleRepository {
    /**
     * Find a appointment sale by its id.
     *
     * @param id the appointment sale id.
     * @return an optional of appointment sale
     */
    Optional<AppointmentSale> findById(Long id);

    /**
     * Save a appointment sale.
     *
     * @param appointmentSale the appointment sale to save
     * @return Result with the saved appointment sale or a failure
     */
    Result<AppointmentSale, RepositoryFailure> save(AppointmentSale appointmentSale);

    /**
     * Delete a appointment sale by its id.
     *
     * @param id the appointment sale id
     * @return Result with success or a failure
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
