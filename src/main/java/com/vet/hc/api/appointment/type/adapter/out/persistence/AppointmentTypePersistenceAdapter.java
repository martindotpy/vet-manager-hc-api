package com.vet.hc.api.appointment.type.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.type.adapter.out.mapper.AppointmentTypeMapper;
import com.vet.hc.api.appointment.type.adapter.out.persistence.repository.AppointmetTypeHibernateRepository;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;
import com.vet.hc.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist appointment types in the database.
 */
@Slf4j
@NoArgsConstructor
public final class AppointmentTypePersistenceAdapter implements AppointmentTypeRepository {
    private AppointmetTypeHibernateRepository appointmetTypeHibernateRepository;

    private final AppointmentTypeMapper appointmentTypeMapper = AppointmentTypeMapper.INSTANCE;

    @Inject
    public AppointmentTypePersistenceAdapter(AppointmetTypeHibernateRepository appointmetTypeHibernateRepository) {
        this.appointmetTypeHibernateRepository = appointmetTypeHibernateRepository;
    }

    @Override
    public List<AppointmentType> findAll() {
        return appointmetTypeHibernateRepository.findAll().stream()
                .map(appointmentTypeMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<AppointmentType> findById(Long id) {
        return appointmetTypeHibernateRepository.findById(id)
                .map(appointmentTypeMapper::toDomain);
    }

    @Override
    public Result<AppointmentType, RepositoryFailure> save(AppointmentType appointmentType) {
        try {
            return Result.success(appointmentTypeMapper
                    .toDomain(appointmetTypeHibernateRepository.save(appointmentTypeMapper.toEntity(appointmentType))));
        } catch (Exception e) {
            log.error("Error saving appointment type: {}", appointmentType, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            appointmetTypeHibernateRepository.deleteById(id);

            return Result.success();
        } catch (Exception e) {
            log.error("Error deleting appointment type with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }
}
