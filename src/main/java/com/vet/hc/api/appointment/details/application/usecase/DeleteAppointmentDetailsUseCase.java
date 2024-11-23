package com.vet.hc.api.appointment.details.application.usecase;

import com.vet.hc.api.appointment.details.application.port.in.DeleteAppointmentDetailsPort;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete an appointment details.
 */
@Slf4j
@NoArgsConstructor
public final class DeleteAppointmentDetailsUseCase implements DeleteAppointmentDetailsPort {
    private AppointmentDetailsRepository appointmentDetailsRepository;

    @Inject
    public DeleteAppointmentDetailsUseCase(AppointmentDetailsRepository appointmentDetailsRepository) {
        this.appointmentDetailsRepository = appointmentDetailsRepository;
    }

    @Override
    public Result<Void, AppointmentDetailsFailure> deleteById(Long id) {
        log.info("Deleting appointment details with id: {}", id);

        var result = appointmentDetailsRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            if (repositoryFailure == RepositoryFailure.NOT_FOUND) {
                log.error("Appointment details with id {} not found", id);

                return Result.failure(AppointmentDetailsFailure.NOT_FOUND);
            }

            log.error("Error deleting appointment details with id {}", id,
                    repositoryFailure);

            return Result.failure(AppointmentDetailsFailure.UNEXPECTED);
        }

        log.info("Appointment details with id {} deleted", id);

        return Result.success();
    }
}
