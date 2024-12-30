package com.vet.hc.api.bill.appointmentsale.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.appointmentsale.application.port.in.DeleteAppointmentSalePort;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.bill.appointmentsale.domain.repository.AppointmentSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a appointmentSale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteAppointmentSaleUseCase implements DeleteAppointmentSalePort {
    private final AppointmentSaleRepository appointmentSaleRepository;

    @Override
    public Result<Void, AppointmentSaleFailure> deleteById(Long id) {
        log.info("Deleting appointmentSale with id: {}", id);

        var result = appointmentSaleRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(AppointmentSaleFailure.UNEXPECTED);
        }

        log.info("Appointment sale with id {} deleted", id);

        return Result.success();
    }
}
