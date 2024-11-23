package com.vet.hc.api.appointment.core.application.port.in;

import java.io.OutputStream;

/**
 * Port for generating a appointment Excel file.
 */
public interface GenerateAppointmentExcelPort {
    /**
     * Generates an Excel file with all appointments.
     *
     * @return The Excel file
     */
    void generateExcel(OutputStream outputStream);
}
