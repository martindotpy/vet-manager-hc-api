package com.vet.hc.api.bill.appointmentsale.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.bill.appointmentsale.adapter.out.mapper.AppointmentSaleMapper;
import com.vet.hc.api.bill.appointmentsale.adapter.out.persistence.repository.AppointmentSaleHibernateRepository;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale;
import com.vet.hc.api.bill.appointmentsale.domain.repository.AppointmentSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist appointment sales in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class AppointmentSalePersistenceAdapter implements AppointmentSaleRepository {
    private final AppointmentSaleHibernateRepository appointmentSaleHibernateRepository;
    private final AppointmentSaleMapper appointmentSaleMapper;

    @Override
    public Optional<AppointmentSale> findById(Long id) {
        return appointmentSaleHibernateRepository.findById(id)
                .map(appointmentSaleMapper::toDomain);
    }

    @Override
    public Result<AppointmentSale, AppointmentSaleFailure> save(AppointmentSale appointmentSale) {
        try {
            return Result.success(
                    appointmentSaleMapper.toDomain(
                            appointmentSaleHibernateRepository.save(appointmentSaleMapper.toEntity(appointmentSale))));
        } catch (ConstraintViolationException e) {
            return Result.failure(AppointmentSaleFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(AppointmentSaleFailure.UNEXPECTED);

            log.error("Error saving appointment sale: {}", appointmentSale, e);

            return Result.failure(AppointmentSaleFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(AppointmentSaleFailure.UNEXPECTED);

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(AppointmentSaleFailure.UNEXPECTED);

            log.error("Error saving appointment sale: {}", appointmentSale, e);

            return Result.failure(AppointmentSaleFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, AppointmentSaleFailure> deleteById(Long id) {
        try {
            appointmentSaleHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Appointment sale with id {} not found", id);

            return Result.failure(AppointmentSaleFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting appointment sale with id: {}", id, e);

            return Result.failure(AppointmentSaleFailure.UNEXPECTED);
        }
    }
}
