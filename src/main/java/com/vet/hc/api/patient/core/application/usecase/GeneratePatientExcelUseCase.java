package com.vet.hc.api.patient.core.application.usecase;

import java.io.OutputStream;
import java.util.List;

import com.vet.hc.api.patient.core.application.mapper.PatientMapper;
import com.vet.hc.api.patient.core.application.port.in.GeneratePatientExcelPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to generate an Excel file with the patients.
 */
@NoArgsConstructor
public class GeneratePatientExcelUseCase implements GeneratePatientExcelPort {
    private PatientRepository patientRepository;
    private GenerateExcelFromTablePort<PatientDto> generateExcelFromTablePort;

    private PatientMapper patientMapper = PatientMapper.INSTANCE;

    @Inject
    public GeneratePatientExcelUseCase(PatientRepository patientRepository,
            GenerateExcelFromTablePort<PatientDto> generateExcelFromTablePort) {
        this.patientRepository = patientRepository;
        this.generateExcelFromTablePort = generateExcelFromTablePort;
    }

    @Override
    public void generateExcel(OutputStream outputStream) {
        List<PatientDto> patients = patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();

        generateExcelFromTablePort.generateExcel(outputStream, "Patientes", patients, PatientDto.class);
    }
}
