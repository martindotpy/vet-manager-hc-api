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
import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create an appointment details.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateAppointmentDetailsUseCase implements CreateAppointmentDetailsPort {
    private final AppointmentDetailsRepository appointmentDetailsRepository;
    private final AppointmentDetailsMapper appointmentDetailsMapper;

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
            return Result.failure(AppointmentDetailsFailure.UNEXPECTED);
        }

        AppointmentDetails createdAppointmentDetails = result.getSuccess();

        log.info("Appointment details created with id `{}`", createdAppointmentDetails.getId());

        return Result.success(appointmentDetailsMapper.toDto(createdAppointmentDetails));
    }
}
