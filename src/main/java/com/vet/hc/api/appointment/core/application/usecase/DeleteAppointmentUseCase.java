package com.vet.hc.api.appointment.core.application.usecase;

import com.vet.hc.api.appointment.core.application.port.in.DeleteAppointmentPort;
import com.vet.hc.api.appointment.core.domain.failure.AppointmentFailure;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete an appointment .
 */
@Slf4j
@NoArgsConstructor
public final class DeleteAppointmentUseCase implements DeleteAppointmentPort {
    private AppointmentRepository appointmentRepository;

    @Inject
    public DeleteAppointmentUseCase(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Result<Void, AppointmentFailure> deleteById(Long id) {
        log.info("Deleting appointment with id: {}", id);

        var result = appointmentRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            if (repositoryFailure == RepositoryFailure.NOT_FOUND) {
                log.error("Appointment with id {} not found", id);

                return Result.failure(AppointmentFailure.NOT_FOUND);
            }

            log.error("Error deleting appointment with id {}", id, repositoryFailure);

            return Result.failure(AppointmentFailure.UNEXPECTED);
        }

        log.info("Appointment with id {} deleted", id);

        return Result.success();
    }
}
