package com.vet.hc.api.client.application.port.in;

import java.io.OutputStream;

/**
 * Port for generating a client Excel file.
 */
public interface GenerateClientExcelPort {
    /**
     * Generates an Excel file with all clients.
     *
     * @return The Excel file
     */
    void generateExcel(OutputStream outputStream);
}
