package com.vet.hc.api.appointment.core.application.usecase;

import java.io.OutputStream;
import java.util.List;

import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.application.port.in.GenerateAppointmentExcelPort;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;

import lombok.RequiredArgsConstructor;

/**
 * Service to generate an Excel file with the appointments.
 */
@UseCase
@RequiredArgsConstructor
public final class GenerateAppointmentExcelUseCase implements GenerateAppointmentExcelPort {
    private final AppointmentRepository appointmentRepository;
    private final GenerateExcelFromTablePort<AppointmentDto> generateExcelFromTablePort;
    private final AppointmentMapper appointmentMapper;

    @Override
    public void generateExcel(OutputStream outputStream) {
        List<AppointmentDto> appointments = appointmentRepository.findAll().stream()
                .map(appointmentMapper::toDto)
                .toList();

        generateExcelFromTablePort.generateExcel(outputStream, "Appointments", appointments, AppointmentDto.class);
    }
}
