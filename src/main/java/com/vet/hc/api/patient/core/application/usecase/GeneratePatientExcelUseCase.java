package com.vet.hc.api.patient.core.application.usecase;

import java.io.ByteArrayOutputStream;

import com.vet.hc.api.patient.core.application.port.in.GeneratePatientExcelPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GeneratePatientExcelUseCase implements GeneratePatientExcelPort {

    @Override
    public void generateExcel(ByteArrayOutputStream outputStream) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateExcel'");
    }
}
