package com.vluepixel.vetmanager.api.appointment.type.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.appointment.type.application.mapper.AppointmentTypeMapper;
import com.vluepixel.vetmanager.api.appointment.type.application.port.in.FindAppointmentTypePort;
import com.vluepixel.vetmanager.api.appointment.type.domain.model.AppointmentType;
import com.vluepixel.vetmanager.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Find appointment type use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindAppointmentTypeUseCase implements FindAppointmentTypePort {
    private final AppointmentTypeRepository appointmentTypeRepository;
    private final AppointmentTypeMapper appointmentTypeMapper;

    @Override
    public Paginated<AppointmentTypeDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Appointment types by criteria: " + fgBrightBlue(criteria.hashCode()));
        log.info("Finding appointment types by {}",
                fgBrightBlue(criteria));

        Paginated<AppointmentType> paginatedAppointmentTypes = appointmentTypeRepository.findPaginatedBy(criteria);

        log.info("{} appointment types found",
                fgBrightGreen(paginatedAppointmentTypes.getContent().size()));

        return paginatedAppointmentTypes.map(appointmentTypeMapper::toDto);
    }

    @Override
    public AppointmentTypeDto findById(Integer id) {
        MDC.put("operationId", "Appointment type id " + id);
        log.info("Finding appointment type by id");

        AppointmentType appointmentType = appointmentTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(AppointmentType.class, id));

        log.info("Appointment type found");

        return appointmentTypeMapper.toDto(appointmentType);
    }
}
