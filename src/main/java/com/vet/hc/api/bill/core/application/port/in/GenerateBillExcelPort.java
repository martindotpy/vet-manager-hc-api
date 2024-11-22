package com.vet.hc.api.bill.core.application.port.in;

import java.io.OutputStream;

/**
 * Port for generating a bill Excel file.
 */
public interface GenerateBillExcelPort {
    /**
     * Generates an Excel file with all bills.
     *
     * @return The Excel file
     */
    void generateExcel(OutputStream outputStream);
}
