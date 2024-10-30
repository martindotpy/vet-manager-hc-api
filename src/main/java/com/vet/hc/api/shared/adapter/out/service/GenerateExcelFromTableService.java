package com.vet.hc.api.shared.adapter.out.service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;
import com.vet.hc.api.shared.domain.spanish.SpanishPropertyName;

/**
 * Service for generating an Excel file from a table.
 */
public class GenerateExcelFromTableService<T> implements GenerateExcelFromTablePort<T> {
    @Override
    public void generateExcel(OutputStream outputStream, String sheetName, List<T> data, Class<T> clazz) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);

            Row headerRow = sheet.createRow(0);

            Field[] fields = clazz.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                if (fields[i].isAnnotationPresent(SpanishPropertyName.class)) {
                    SpanishPropertyName annotation = fields[i].getAnnotation(SpanishPropertyName.class);
                    headerRow.createCell(i).setCellValue(annotation.value());
                } else {
                    headerRow.createCell(i).setCellValue(fields[i].getName());
                }
            }

            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i + 1);

                for (int j = 0; j < fields.length; j++) {
                    fields[j].setAccessible(true);
                    row.createCell(j).setCellValue(fields[j].get(data.get(i)).toString());
                }
            }

            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel file", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing field", e);
        }
    }
}
