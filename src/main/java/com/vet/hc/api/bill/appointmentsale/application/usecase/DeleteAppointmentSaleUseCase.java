package com.vet.hc.api.bill.appointmentsale.application.usecase;

import com.vet.hc.api.bill.appointmentsale.application.port.in.DeleteAppointmentSalePort;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.bill.appointmentsale.domain.repository.AppointmentSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a appointmentSale.
 */
@Slf4j
@NoArgsConstructor
public final class DeleteAppointmentSaleUseCase implements DeleteAppointmentSalePort {
    private AppointmentSaleRepository appointmentSaleRepository;

    @Inject
    public DeleteAppointmentSaleUseCase(AppointmentSaleRepository appointmentSaleRepository) {
        this.appointmentSaleRepository = appointmentSaleRepository;
    }

    @Override
    public Result<Void, AppointmentSaleFailure> deleteById(Long id) {
        log.info("Deleting appointmentSale with id: {}", id);

        var result = appointmentSaleRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case NOT_FOUND -> {
                    log.error("Appointment sale with id {} not found", id);

                    yield Result.failure(AppointmentSaleFailure.NOT_FOUND);
                }
                default -> {
                    log.error("Unexpected error deleting appointment sale with id {}", id);

                    yield Result.failure(AppointmentSaleFailure.UNEXPECTED);
                }
            };
        }

        log.info("Appointment sale with id {} deleted", id);

        return Result.success();
    }
}
