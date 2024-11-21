package com.vet.hc.api.appointment.details.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository for appointment details.
 */
public interface AppointmentDetailsRepository {
    /**
     * Find all appointment details.
     *
     * @return The list of appointment details.
     */
    List<AppointmentDetails> findAll();

    /**
     * Find a appointmentDetails by id.
     *
     * @param id The id of the appointmentDetails to find.
     * @return The appointmentDetails with the given id.
     */
    Optional<AppointmentDetails> findById(Long id);

    /**
     * Create a new appointmentDetails.
     *
     * @param appointmentDetails The appointmentDetails to create.
     * @return The created appointmentDetails
     */
    AppointmentDetails save(AppointmentDetails appointmentDetails);

    /**
     * Find appointment details that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @return The list of appointment details that match the criteria, the failure
     *         otherwise.
     *         The
     *         failure can be:
     *         <ul>
     *         <li>{@link RepositoryFailure#FIELD_NOT_FOUND} if the field in the
     *         criteria is not found</li>
     *         <li>{@link RepositoryFailure#UNEXPECTED} if an internal error
     *         occurred
     *         while saving the appointmentDetails phone.</li>
     *         </ul>
     */
    Result<Paginated<AppointmentDetails>, RepositoryFailure> match(Criteria criteria);

    /**
     * Delete a appointmentDetails.
     *
     * @param id The id of the appointmentDetails to delete.
     * @return The result of the operation
     */
    Result<Void, AppointmentDetailsFailure> deleteById(Long id);
}
