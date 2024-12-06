package com.vet.hc.api.patient.core.application.usecase;

import java.io.OutputStream;
import java.util.List;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.core.application.mapper.PatientMapper;
import com.vet.hc.api.patient.core.application.port.in.GeneratePatientExcelPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;

import lombok.RequiredArgsConstructor;

/**
 * Service to generate an Excel file with the patients.
 */
@UseCase
@RequiredArgsConstructor
public final class GeneratePatientExcelUseCase implements GeneratePatientExcelPort {
    private final PatientRepository patientRepository;
    private final GenerateExcelFromTablePort<PatientDto> generateExcelFromTablePort;
    private final PatientMapper patientMapper;

    @Override
    public void generateExcel(OutputStream outputStream) {
        List<PatientDto> patients = patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();

        generateExcelFromTablePort.generateExcel(outputStream, "Pacientes", patients, PatientDto.class);
    }
}
