package com.vet.hc.api.appointment.details.application.usecase;

import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.details.adapter.out.mapper.AppointmentDetailsMapper;
import com.vet.hc.api.appointment.details.application.port.in.CreateAppointmentDetailsPort;
import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;
import com.vet.hc.api.appointment.details.domain.payload.CreateAppointmentDetailsPayload;
import com.vet.hc.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create an appointment details.
 */
@Slf4j
@NoArgsConstructor
public final class CreateAppointmentDetailsUseCase implements CreateAppointmentDetailsPort {
    private AppointmentDetailsRepository appointmentDetailsRepository;

    private final AppointmentDetailsMapper appointmentDetailsMapper = AppointmentDetailsMapper.INSTANCE;

    @Inject
    public CreateAppointmentDetailsUseCase(AppointmentDetailsRepository appointmentDetailsRepository) {
        this.appointmentDetailsRepository = appointmentDetailsRepository;
    }

    @Override
    public Result<AppointmentDetailsDto, AppointmentDetailsFailure> create(CreateAppointmentDetailsPayload payload) {
        log.info("Creating appointment details with type id `{}`", payload.getAppointmentTypeId());

        AppointmentDetails appointmentDetails = AppointmentDetails.builder()
                .durationInMinutes(payload.getDurationInMinutes())
                .price(payload.getPrice())
                .type(AppointmentType.builder()
                        .id(payload.getAppointmentTypeId())
                        .build())
                .appointment(Appointment.builder()
                        .id(payload.getAppointmentId())
                        .build())
                .build();

        var result = appointmentDetailsRepository.save(appointmentDetails);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            if (failure == RepositoryFailure.ENTITY_NOT_FOUND) {
                if (failure.getField().equals("appointment")) {
                    log.error("Failed to create appointment details: appointment not found");

                    return Result.failure(AppointmentDetailsFailure.APPOINTMENT_NOT_FOUND);
                }

                else if (failure.getField().equals("appointment_type")) {
                    log.error("Failed to create appointment details: appointment type not found");

                    return Result.failure(AppointmentDetailsFailure.APPOINTMENT_TYPE_NOT_FOUND);
                }

                return Result.failure(AppointmentDetailsFailure.UNEXPECTED);
            }

            log.error("Failed to create appointment details: {}", failure.getMessage());

            return Result.failure(AppointmentDetailsFailure.UNEXPECTED);
        }

        AppointmentDetails createdAppointmentDetails = result.getSuccess();

        log.info("Appointment details created with id `{}`", createdAppointmentDetails.getId());

        return Result.success(appointmentDetailsMapper.toDto(createdAppointmentDetails));
    }
}
