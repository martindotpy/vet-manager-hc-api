package com.vet.hc.api.appointment.details.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.appointment.details.adapter.out.mapper.AppointmentDetailsMapper;
import com.vet.hc.api.appointment.details.adapter.out.persistence.repository.AppointmentDetailsHibernateRepository;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;
import com.vet.hc.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist appointment details in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class AppointmentDetailsPersistenceAdapter implements AppointmentDetailsRepository {
    private final AppointmentDetailsHibernateRepository appointmentDetailsHibernateRepository;
    private final AppointmentDetailsMapper appointmentDetailsMapper;

    @Override
    public Optional<AppointmentDetails> findById(Long id) {
        return appointmentDetailsHibernateRepository.findById(id)
                .map(appointmentDetailsMapper::toDomain);
    }

    @Override
    public Result<AppointmentDetails, AppointmentDetailsFailure> save(AppointmentDetails appointmentDetails) {
        try {
            return Result.success(appointmentDetailsMapper
                    .toDomain(appointmentDetailsHibernateRepository
                            .save(appointmentDetailsMapper.toEntity(appointmentDetails))));
        } catch (ConstraintViolationException e) {
            return Result.failure(AppointmentDetailsFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(AppointmentDetailsFailure.UNEXPECTED);

            log.error("Error saving appointment details: {}", appointmentDetails, e);

            return Result.failure(AppointmentDetailsFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(AppointmentDetailsFailure.UNEXPECTED);

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(AppointmentDetailsFailure.NOT_FOUND);

            log.error("Error saving appointment details: {}", appointmentDetails, e);

            return Result.failure(AppointmentDetailsFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, AppointmentDetailsFailure> deleteById(Long id) {
        try {
            appointmentDetailsHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Appointment details with id {} not found", id);

            return Result.failure(AppointmentDetailsFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting appointment details with id: {}", id, e);

            return Result.failure(AppointmentDetailsFailure.UNEXPECTED);
        }
    }
}
