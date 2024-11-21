package com.vet.hc.api.appointment.type.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.type.domain.model.AppointmentType;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository interface for {@link AppointmentType} model.
 */
public interface AppointmentTypeRepository {
    /**
     * Find all appointment types.
     *
     * @return a list of appointment types
     */
    List<AppointmentType> findAll();

    /**
     * Find an appointment type by its id.
     *
     * @param id the appointment type id.
     * @return an optional of appointment type
     */
    Optional<AppointmentType> findById(Long id);

    /**
     * Save an appointment type.
     *
     * @param appointmentType the appointment type to save
     * @return Result with the saved appointment type or a failure
     */
    Result<AppointmentType, RepositoryFailure> save(AppointmentType appointmentType);

    /**
     * Delete an appointment type by its id.
     *
     * @param id the appointment type id
     * @return Result with success or a failure
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
