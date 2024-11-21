package com.vet.hc.api.patient.core.application.port.in;

import java.io.ByteArrayOutputStream;

public interface GeneratePatientExcelPort {
    void generateExcel(ByteArrayOutputStream outputStream);
}
