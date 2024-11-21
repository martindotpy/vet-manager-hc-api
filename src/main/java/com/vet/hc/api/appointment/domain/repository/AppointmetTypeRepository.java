package com.vet.hc.api.appointment.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.appointment.domain.model.AppointmentType;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Repository for appointment types.
 */
public interface AppointmetTypeRepository {
    /**
     * Find all appointment types.
     *
     * @return The list of appointment types.
     */
    List<AppointmentType> findAll();

    /**
     * Find a appointment type by id.
     *
     * @param id The id of the appointment type to find.
     * @return The appointment type with the given id.
     */
    Optional<AppointmentType> findById(Long id);

    /**
     * Create a new appointment type.
     *
     * @param appointmentType The appointment type to create.
     * @return The result of the operation
     */
    Result<AppointmentType, AppointmentTypeFailure> save(AppointmentType appointmentType);

    /**
     * Delete a appointment type.
     *
     * @param id The id of the appointment type to delete.
     * @return The result of the operation
     */
    Result<Void, AppointmentTypeFailure> deleteById(Long id);
}
