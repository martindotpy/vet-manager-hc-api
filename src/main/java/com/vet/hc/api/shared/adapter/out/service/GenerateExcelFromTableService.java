package com.vet.hc.api.shared.adapter.out.service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;
import com.vet.hc.api.shared.domain.spanish.SpanishPropertyName;

/**
 * Service for generating an Excel file from a table.
 *
 * <p>
 * If the field has the {@link SpanishPropertyName} annotation, the value of the
 * annotation will be used as the header
 * name. Otherwise, the field name will be used.
 * </p>
 *
 * <p>
 * Also, if the field is an enum, the value of the annotation of the enum
 * constant will be used as the value. Otherwise,
 * the name of the enum constant will be used.
 * </p>
 *
 * @see SpanishPropertyName
 */
@Component
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

                    if (fields[j].getType().isEnum()) {
                        Enum<?> value = (Enum<?>) fields[j].get(data.get(i));
                        Field enumField = value.getDeclaringClass().getField(value.name());

                        if (enumField.isAnnotationPresent(SpanishPropertyName.class)) {
                            SpanishPropertyName annotation = enumField.getAnnotation(SpanishPropertyName.class);
                            row.createCell(j).setCellValue(annotation.value());
                        } else {
                            row.createCell(j).setCellValue(value.name());
                        }
                    } else {
                        Object value = fields[j].get(data.get(i));

                        if (value != null) {
                            if (value instanceof Number) {
                                row.createCell(j).setCellValue(((Number) value).doubleValue());
                            } else {
                                row.createCell(j).setCellValue(value.toString());
                            }
                        } else {
                            row.createCell(j).setCellValue("");
                        }
                    }
                }
            }

            for (int i = 0; i < fields.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel file", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing field", e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Error accessing field", e);
        } catch (SecurityException e) {
            throw new RuntimeException("Error accessing field", e);
        }
    }
}
