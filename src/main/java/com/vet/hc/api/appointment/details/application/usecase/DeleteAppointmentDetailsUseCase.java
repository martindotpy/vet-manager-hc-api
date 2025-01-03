package com.vet.hc.api.appointment.details.application.usecase;

import com.vet.hc.api.appointment.details.application.port.in.DeleteAppointmentDetailsPort;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete an appointment details.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteAppointmentDetailsUseCase implements DeleteAppointmentDetailsPort {
    private final AppointmentDetailsRepository appointmentDetailsRepository;

    @Override
    public Result<Void, AppointmentDetailsFailure> deleteById(Long id) {
        log.info("Deleting appointment details with id: {}", id);

        var result = appointmentDetailsRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(AppointmentDetailsFailure.UNEXPECTED);
        }

        log.info("Appointment details with id {} deleted", id);

        return Result.success();
    }
}
