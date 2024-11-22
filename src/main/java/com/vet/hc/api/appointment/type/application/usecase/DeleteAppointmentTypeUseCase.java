package com.vet.hc.api.appointment.type.application.usecase;

import com.vet.hc.api.appointment.type.application.port.in.DeleteAppointmentTypePort;
import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete an appointment type.
 */
@Slf4j
@NoArgsConstructor
public final class DeleteAppointmentTypeUseCase implements DeleteAppointmentTypePort {
    private AppointmentTypeRepository appointmentTypeRepository;

    @Inject
    public DeleteAppointmentTypeUseCase(AppointmentTypeRepository appointmentTypeRepository) {
        this.appointmentTypeRepository = appointmentTypeRepository;
    }

    @Override
    public Result<Void, AppointmentTypeFailure> deleteById(Long id) {
        log.info("Deleting appointment type with id: {}", id);

        var result = appointmentTypeRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            if (repositoryFailure == RepositoryFailure.NOT_FOUND) {
                log.error("Appointment type with id {} not found", id);

                return Result.failure(AppointmentTypeFailure.NOT_FOUND);
            }

            log.error("Error deleting appointment type with id {}", id, repositoryFailure);

            return Result.failure(AppointmentTypeFailure.UNEXPECTED);
        }

        log.info("Appointment type with id {} deleted", id);

        return Result.success();
    }
}
