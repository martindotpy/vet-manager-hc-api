package com.vet.hc.api.appointment.type.application.usecase;

import com.vet.hc.api.appointment.type.adapter.out.mapper.AppointmentTypeMapper;
import com.vet.hc.api.appointment.type.application.port.in.CreateAppointmentTypePort;
import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;
import com.vet.hc.api.appointment.type.domain.payload.CreateAppointmentTypePayload;
import com.vet.hc.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create an appointment type.
 */
@Slf4j
@NoArgsConstructor
public final class CreateAppointmentTypeUseCase implements CreateAppointmentTypePort {
    private AppointmentTypeRepository appointmentTypeRepository;

    private final AppointmentTypeMapper appointmentTypeMapper = AppointmentTypeMapper.INSTANCE;

    @Inject
    public CreateAppointmentTypeUseCase(AppointmentTypeRepository appointmentTypeRepository) {
        this.appointmentTypeRepository = appointmentTypeRepository;
    }

    @Override
    public Result<AppointmentTypeDto, AppointmentTypeFailure> create(CreateAppointmentTypePayload payload) {
        log.info("Creating appointment type: {}", payload.getName());

        AppointmentType appointmentType = AppointmentType.builder()
                .name(payload.getName())
                .durationInMinutes(payload.getDurationInMinutes())
                .price(payload.getPrice())
                .build();

        var result = appointmentTypeRepository.save(appointmentType);

        if (result.isFailure()) {
            log.error("Failed to create appointment type: {}", result.getFailure().getMessage());

            RepositoryFailure failure = result.getFailure();

            if (failure == RepositoryFailure.DUPLICATED) {
                if (failure.getField().equals("name"))
                    return Result.failure(AppointmentTypeFailure.DUPLICATED_NAME);
                else
                    return Result.failure(AppointmentTypeFailure.UNEXPECTED);
            }

            return Result.failure(AppointmentTypeFailure.UNEXPECTED);
        }

        AppointmentType createdAppointmentType = result.getSuccess();

        log.info("Appointment type created: {}", createdAppointmentType.getName());

        return Result.success(appointmentTypeMapper.toDto(createdAppointmentType));
    }
}
