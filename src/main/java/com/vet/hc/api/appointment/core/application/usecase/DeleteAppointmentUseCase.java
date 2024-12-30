package com.vet.hc.api.appointment.core.application.usecase;

import com.vet.hc.api.appointment.core.application.port.in.DeleteAppointmentPort;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete an appointment .
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteAppointmentUseCase implements DeleteAppointmentPort {
    private final AppointmentRepository appointmentRepository;

    @Override
    public Result<Void, AppointmentFailure> deleteById(Long id) {
        log.info("Deleting appointment with id: {}", id);

        var result = appointmentRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(AppointmentFailure.UNEXPECTED);
        }

        log.info("Appointment with id {} deleted", id);

        return Result.success();
    }
}
