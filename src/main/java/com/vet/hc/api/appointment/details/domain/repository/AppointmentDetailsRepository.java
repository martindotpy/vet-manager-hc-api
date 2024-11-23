package com.vet.hc.api.appointment.details.domain.repository;

import java.util.Optional;

import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository interface for {@link AppointmentDetails} model.
 */
public interface AppointmentDetailsRepository {
    /**
     * Find an appointment details by its id.
     *
     * @param id the appointment details id.
     * @return an optional of appointment details
     */
    Optional<AppointmentDetails> findById(Long id);

    /**
     * Save an appointment details.
     *
     * @param appointmentDetails the appointment details to save
     * @return Result with the saved appointment details or a failure
     */
    Result<AppointmentDetails, RepositoryFailure> save(AppointmentDetails appointmentDetails);

    /**
     * Delete an appointment details by its id.
     *
     * @param id the appointment details id
     * @return Result with success or a failure
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}