package com.vet.hc.api.appointment.details.application.usecase;

import java.util.Optional;

import com.vet.hc.api.appointment.details.adapter.out.mapper.AppointmentDetailsMapper;
import com.vet.hc.api.appointment.details.application.port.in.UpdateAppointmentDetailsPort;
import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;
import com.vet.hc.api.appointment.details.domain.payload.UpdateAppointmentDetailsPayload;
import com.vet.hc.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update an appointment details.
 */
@Slf4j
@NoArgsConstructor
public final class UpdateAppointmentDetailsUseCase implements UpdateAppointmentDetailsPort {
    private AppointmentDetailsRepository appointmentDetailsRepository;

    private final AppointmentDetailsMapper appointmentDetailsMapper = AppointmentDetailsMapper.INSTANCE;

    @Inject
    public UpdateAppointmentDetailsUseCase(AppointmentDetailsRepository appointmentDetailsRepository) {
        this.appointmentDetailsRepository = appointmentDetailsRepository;
    }

    @Override
    public Result<AppointmentDetailsDto, AppointmentDetailsFailure> update(UpdateAppointmentDetailsPayload payload) {
        log.info("Updating appointment details with id {}", payload.getId());

        Optional<AppointmentDetails> appointmentDetails = appointmentDetailsRepository.findById(payload.getId());

        if (appointmentDetails.isEmpty()) {
            log.error("Appointment details with id {} not found", payload.getId());

            return Result.failure(AppointmentDetailsFailure.NOT_FOUND);
        }

        AppointmentDetails appointmentDetailsFound = appointmentDetails.get();
        AppointmentDetails appointmentDetailsToUpdate = AppointmentDetails.builder()
                .id(payload.getId())
                .durationInMinutes(payload.getDurationInMinutes())
                .price(payload.getPrice())
                .type(AppointmentType.builder()
                        .id(payload.getAppointmentTypeId())
                        .build())
                .appointment(appointmentDetailsFound.getAppointment())
                .build();

        var result = appointmentDetailsRepository.save(appointmentDetailsToUpdate);

        if (result.isFailure()) {
            log.error("Error updating appointment details: {}", result.getFailure());

            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case DUPLICATED -> {
                    yield Result.failure(AppointmentDetailsFailure.UNEXPECTED);
                }
                default -> Result.failure(AppointmentDetailsFailure.UNEXPECTED);
            };
        }

        AppointmentDetails appointmentDetailsUpdated = result.getSuccess();

        log.info("Appointment details with id {} updated", appointmentDetailsUpdated.getId());

        return Result.success(appointmentDetailsMapper.toDto(appointmentDetailsUpdated));
    }
}