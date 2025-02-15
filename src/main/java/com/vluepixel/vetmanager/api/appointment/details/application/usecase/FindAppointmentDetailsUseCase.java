package com.vluepixel.vetmanager.api.appointment.details.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.appointment.details.application.mapper.AppointmentDetailsMapper;
import com.vluepixel.vetmanager.api.appointment.details.application.port.in.FindAppointmentDetailsPort;
import com.vluepixel.vetmanager.api.appointment.details.domain.model.AppointmentDetails;
import com.vluepixel.vetmanager.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Find appointment details use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindAppointmentDetailsUseCase implements FindAppointmentDetailsPort {
    private final AppointmentDetailsRepository appointmentDetailsRepository;
    private final AppointmentDetailsMapper appointmentDetailsMapper;

    @Override
    public Paginated<AppointmentDetailsDto> findPaginatedBy(PaginatedCriteria criteria) {
        MDC.put("operationId", "Appointment types by criteria: " + fgBrightBlue(criteria.hashCode()));
        log.info("Finding appointment details by {}",
                fgBrightBlue(criteria));

        Paginated<AppointmentDetails> paginatedAppointmentDetails = appointmentDetailsRepository
                .findPaginatedBy(criteria);

        log.info("{} appointment details found",
                fgBrightGreen(paginatedAppointmentDetails.getContent().size()));

        return paginatedAppointmentDetails.map(appointmentDetailsMapper::toDto);
    }

    @Override
    public AppointmentDetailsDto findById(Long id) {
        MDC.put("operationId", "Appointment details id " + id);
        log.info("Finding appointment details by id");

        AppointmentDetails appointmentDetails = appointmentDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(AppointmentDetails.class, id));

        log.info("Appointment details found");

        return appointmentDetailsMapper.toDto(appointmentDetails);
    }
}
