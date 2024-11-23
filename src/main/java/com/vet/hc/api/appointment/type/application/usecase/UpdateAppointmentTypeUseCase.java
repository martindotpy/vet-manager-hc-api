package com.vet.hc.api.appointment.type.application.usecase;

import com.vet.hc.api.appointment.type.adapter.out.mapper.AppointmentTypeMapper;
import com.vet.hc.api.appointment.type.application.port.in.UpdateAppointmentTypePort;
import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;
import com.vet.hc.api.appointment.type.domain.payload.UpdateAppointmentTypePayload;
import com.vet.hc.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update an appointment type.
 */
@Slf4j
@NoArgsConstructor
public final class UpdateAppointmentTypeUseCase implements UpdateAppointmentTypePort {
    private AppointmentTypeRepository appointmentTypeRepository;

    private final AppointmentTypeMapper appointmentTypeMapper = AppointmentTypeMapper.INSTANCE;

    @Inject
    public UpdateAppointmentTypeUseCase(AppointmentTypeRepository appointmentTypeRepository) {
        this.appointmentTypeRepository = appointmentTypeRepository;
    }

    @Override
    public Result<AppointmentTypeDto, AppointmentTypeFailure> update(UpdateAppointmentTypePayload payload) {
        log.info("Updating appointment type with id {}", payload.getId());

        AppointmentType appointmentTypeToUpdate = AppointmentType.builder()
                .id(payload.getId())
                .name(payload.getName())
                .durationInMinutes(payload.getDurationInMinutes())
                .price(payload.getPrice())
                .build();

        var result = appointmentTypeRepository.save(appointmentTypeToUpdate);

        if (result.isFailure()) {
            log.error("Error updating appointment type: {}", result.getFailure());

            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case DUPLICATED -> {
                    if (repositoryFailure.getField().equals("name"))
                        yield Result.failure(AppointmentTypeFailure.DUPLICATED_NAME);

                    yield Result.failure(AppointmentTypeFailure.UNEXPECTED);
                }
                default -> Result.failure(AppointmentTypeFailure.UNEXPECTED);
            };
        }

        AppointmentType appointmentType = result.getSuccess();

        log.info("Appointment type with id {} updated", appointmentType.getId());

        return Result.success(appointmentTypeMapper.toDto(appointmentType));
    }
}