package com.vet.hc.api.appointment.core.application.usecase;

import java.io.OutputStream;
import java.util.List;

import com.vet.hc.api.appointment.core.application.mapper.AppointmentMapper;
import com.vet.hc.api.appointment.core.application.port.in.GenerateAppointmentExcelPort;
import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.appointment.core.domain.repository.AppointmentRepository;
import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to generate an Excel file with the appointments.
 */
@NoArgsConstructor
public class GenerateAppointmentExcelUseCase implements GenerateAppointmentExcelPort {
    private AppointmentRepository appointmentRepository;
    private GenerateExcelFromTablePort<AppointmentDto> generateExcelFromTablePort;

    private AppointmentMapper appointmentMapper = AppointmentMapper.INSTANCE;

    @Inject
    public GenerateAppointmentExcelUseCase(AppointmentRepository appointmentRepository,
            GenerateExcelFromTablePort<AppointmentDto> generateExcelFromTablePort) {
        this.appointmentRepository = appointmentRepository;
        this.generateExcelFromTablePort = generateExcelFromTablePort;
    }

    @Override
    public void generateExcel(OutputStream outputStream) {
        List<AppointmentDto> appointments = appointmentRepository.findAll().stream()
                .map(appointmentMapper::toDto)
                .toList();

        generateExcelFromTablePort.generateExcel(outputStream, "Appointmentes", appointments, AppointmentDto.class);
    }
}
