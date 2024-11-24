package com.vet.hc.api.patient.core.application.port.in;

import java.io.OutputStream;

/**
 * Port for generating a patient Excel file.
 */
public interface GeneratePatientExcelPort {
    /**
     * Generates an Excel file with all patients.
     *
     * @return The Excel file
     */
    void generateExcel(OutputStream outputStream);
}
