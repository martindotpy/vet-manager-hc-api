package com.vet.hc.api.appointment.type.application.usecase;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.vet.hc.api.appointment.type.adapter.out.mapper.AppointmentTypeMapper;
import com.vet.hc.api.appointment.type.application.port.in.FindAppointmentTypePort;
import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find an appointment type.
 */
@Slf4j
@NoArgsConstructor
public final class FindAppointmentTypeUseCase implements FindAppointmentTypePort {
    private AppointmentTypeRepository appointmentTypeRepository;

    private final AppointmentTypeMapper appointmentTypeMapper = AppointmentTypeMapper.INSTANCE;

    @Inject
    public FindAppointmentTypeUseCase(AppointmentTypeRepository appointmentTypeRepository) {
        this.appointmentTypeRepository = appointmentTypeRepository;
    }

    @Override
    public List<AppointmentTypeDto> findAll() {
        log.info("Finding all appointment types");

        return appointmentTypeRepository.findAll().stream()
                .map(appointmentTypeMapper::toDto)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    @Override
    public Result<AppointmentTypeDto, AppointmentTypeFailure> findById(Long id) {
        log.info("Finding appointment type by id: {}", id);

        var result = appointmentTypeRepository.findById(id);

        if (result.isEmpty()) {
            log.error("Appointment type not found with id: {}", id);

            return Result.failure(AppointmentTypeFailure.NOT_FOUND);
        }

        log.info("Appointment type found: {}", result.get());

        return Result.success(appointmentTypeMapper.toDto(result.get()));
    }
}
