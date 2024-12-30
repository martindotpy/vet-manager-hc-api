package com.vet.hc.api.appointment.type.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.appointment.type.adapter.out.mapper.AppointmentTypeMapper;
import com.vet.hc.api.appointment.type.adapter.out.persistence.repository.AppointmentTypeHibernateRepository;
import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;
import com.vet.hc.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist appointment types in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class AppointmentTypePersistenceAdapter implements AppointmentTypeRepository {
    private final AppointmentTypeHibernateRepository appointmentTypeHibernateRepository;
    private final AppointmentTypeMapper appointmentTypeMapper;

    @Override
    public List<AppointmentType> findAll() {
        return appointmentTypeHibernateRepository.findAll().stream()
                .map(appointmentTypeMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<AppointmentType> findById(Long id) {
        return appointmentTypeHibernateRepository.findById(id)
                .map(appointmentTypeMapper::toDomain);
    }

    @Override
    public Result<AppointmentType, AppointmentTypeFailure> save(AppointmentType appointmentType) {
        try {
            return Result.success(appointmentTypeMapper
                    .toDomain(
                            appointmentTypeHibernateRepository.save(appointmentTypeMapper.toEntity(appointmentType))));
        } catch (ConstraintViolationException e) {
            return Result.failure(AppointmentTypeFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(AppointmentTypeFailure.UNEXPECTED);

            log.error("Error saving appointment type: {}", appointmentType, e);

            return Result.failure(AppointmentTypeFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(AppointmentTypeFailure.UNEXPECTED);

            log.error("Error saving appointment type: {}", appointmentType, e);

            return Result.failure(AppointmentTypeFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, AppointmentTypeFailure> deleteById(Long id) {
        try {
            appointmentTypeHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Appointment type with id {} not found", id);

            return Result.failure(AppointmentTypeFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting appointment type with id: {}", id, e);

            return Result.failure(AppointmentTypeFailure.UNEXPECTED);
        }
    }
}
