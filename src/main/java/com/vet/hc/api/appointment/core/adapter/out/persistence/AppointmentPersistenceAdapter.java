package com.vet.hc.api.appointment.core.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.core.adapter.out.persistence.repository.AppointmentHibernateRepository;
import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for appointment persistence.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class AppointmentPersistenceAdapter implements AppointmentRepository {
    private final AppointmentHibernateRepository appointmentHibernateRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<Appointment> findAll() {
        return appointmentHibernateRepository.findAll().stream()
                .map(appointmentMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentHibernateRepository.findById(id)
                .map(appointmentMapper::toDomain);
    }

    @Override
    public Result<Paginated<Appointment>, AppointmentFailure> match(Criteria criteria) {
        try {
            var response = appointmentHibernateRepository.match(criteria);

            return Result.success(
                    Paginated.<Appointment>builder()
                            .content(response.getContent().stream()
                                    .map(appointmentMapper::toDomain)
                                    .toList())
                            .page(response.getPage())
                            .size(response.getSize())
                            .totalElements(response.getTotalElements())
                            .totalPages(response.getTotalPages())
                            .build());
        } catch (IllegalArgumentException e) {
            log.warn("Field not found in criteria: {}", e.getMessage());
            return Result.failure(AppointmentFailure.FIELD_NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(AppointmentFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Appointment, AppointmentFailure> save(Appointment appointment) {
        try {
            return Result.success(appointmentMapper
                    .toDomain(appointmentHibernateRepository.save(appointmentMapper.toEntity(appointment))));
        } catch (EntityNotFoundException e) {
            log.error("Error saving appointment", e);

            return Result.failure(AppointmentFailure.UNEXPECTED);
        } catch (Exception e) {
            log.error("Error saving appointment", e);

            return Result.failure(AppointmentFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, AppointmentFailure> deleteById(Long id) {
        try {
            appointmentHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Appointment with id {} not found", id);

            return Result.failure(AppointmentFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting appointment with id: {}", id, e);

            return Result.failure(AppointmentFailure.UNEXPECTED);
        }
    }
}
