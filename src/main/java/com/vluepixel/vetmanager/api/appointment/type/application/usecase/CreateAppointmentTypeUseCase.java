package com.vluepixel.vetmanager.api.appointment.type.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.appointment.type.application.mapper.AppointmentTypeMapper;
import com.vluepixel.vetmanager.api.appointment.type.application.port.in.CreateAppointmentTypePort;
import com.vluepixel.vetmanager.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vluepixel.vetmanager.api.appointment.type.domain.request.CreateAppointmentTypeRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create appointment type use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateAppointmentTypeUseCase implements CreateAppointmentTypePort {
    private final AppointmentTypeRepository appointmentTypeRepository;
    private final AppointmentTypeMapper appointmentTypeMapper;

    @Override
    @Transactional
    public AppointmentTypeDto create(CreateAppointmentTypeRequest request) {
        MDC.put("operationId", "Appointment type name " + request.getName());
        log.info("Creating appointment type");

        var newAppointmentType = appointmentTypeMapper.fromRequest(request).build();
        newAppointmentType = appointmentTypeRepository.save(newAppointmentType);

        log.info("Appointment type created");

        return appointmentTypeMapper.toDto(newAppointmentType);
    }
}
