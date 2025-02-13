package com.vluepixel.vetmanager.api.appointment.type.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.appointment.type.application.mapper.AppointmentTypeMapper;
import com.vluepixel.vetmanager.api.appointment.type.application.port.in.FindAppointmentTypePort;
import com.vluepixel.vetmanager.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find appointment types.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindAppointmentTypeUseCase implements FindAppointmentTypePort {
    private final AppointmentTypeRepository appointmentTypeRepository;
    private final AppointmentTypeMapper appointmentTypeMapper;

    @Override
    public Paginated<AppointmentTypeDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Criteria " + criteria);
        log.info("Finding all appointment types");

        var paginated = appointmentTypeRepository.findPaginatedBy(criteria);

        log.info("Retrieved {} appointment types",
                fgBrightGreen(paginated.getContent().size()));

        return paginated.map(appointmentTypeMapper::toDto);
    }

    @Override
    public AppointmentTypeDto findById(Long id) {
        MDC.put("operationId", "Id " + id);
        log.info("Finding appointment type by id");

        var appointmentType = appointmentTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de cita", id));

        log.info("Retrieved appointment type {}", fgBrightGreen(appointmentType));

        return appointmentTypeMapper.toDto(appointmentType);
    }
}
