package com.vet.hc.api.shared.application.port.out;

import java.io.OutputStream;
import java.util.List;

/**
 * Port for generating an Excel file from a table.
 */
public interface GenerateExcelFromTablePort<T> {
    /**
     * Generates a Excel file as output stream.
     *
     * <p>
     * The headers of the table generated will be the properties of the object T.
     * </p>
     *
     * @param outputStream The output stream to write the Excel file.
     * @param sheetName    The name of the sheet in the Excel file.
     * @param data         The data to be written in the Excel file.
     * @param clazz        The class of the data.
     * @return The Excel file as input stream
     */
    void generateExcel(OutputStream outputStream, String sheetName, List<T> data, Class<T> clazz);
}
