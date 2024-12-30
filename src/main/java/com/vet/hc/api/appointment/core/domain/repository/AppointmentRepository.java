package com.vet.hc.api.appointment.core.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailureType;

/**
 * Repository for appointments.
 */
public interface AppointmentRepository {
    /**
     * Find all appointments.
     *
     * @return The list of appointments.
     */
    List<Appointment> findAll();

    /**
     * Find a appointment by id.
     *
     * @param id The id of the appointment to find.
     * @return The appointment with the given id.
     */
    Optional<Appointment> findById(Long id);

    /**
     * Create a new appointment.
     *
     * @param appointment The appointment to create.
     * @return The created appointment
     */
    Result<Appointment, AppointmentFailure> save(Appointment appointment);

    /**
     * Find appointments that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @return The list of appointments that match the criteria, the failure
     *         otherwise.
     *         The
     *         failure can be:
     *         <ul>
     *         <li>{@link RepositoryFailureType#FIELD_NOT_FOUND} if the field in the
     *         criteria is not found</li>
     *         <li>{@link RepositoryFailureType#UNEXPECTED} if an internal error
     *         occurred
     *         while saving the appointment phone.</li>
     *         </ul>
     */
    Result<Paginated<Appointment>, AppointmentFailure> match(Criteria criteria);

    /**
     * Delete a appointment.
     *
     * @param id The id of the appointment to delete.
     * @return The result of the operation
     */
    Result<Void, AppointmentFailure> deleteById(Long id);
}
