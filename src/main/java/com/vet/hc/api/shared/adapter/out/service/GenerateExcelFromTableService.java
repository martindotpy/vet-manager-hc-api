package com.vet.hc.api.shared.adapter.out.service;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for generating an Excel file from a table.
 *
 * <p>
 * If the field has the {@link ColumnPropertyName} annotation, the value of the
 * annotation will be used as the header name. Otherwise, the field name will be
 * used.
 * </p>
 *
 * <p>
 * Also, if the field is an enum, the value of the annotation of the enum
 * constant will be used as the value. Otherwise, the name of the enum constant
 * will be used.
 * </p>
 *
 * @see ColumnPropertyName
 */
@Slf4j
@Component
public class GenerateExcelFromTableService<T> implements GenerateExcelFromTablePort<T> {
    @Override
    public void generateExcel(OutputStream outputStream, String sheetName, List<T> data, Class<T> clazz) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Map<Class<?>, List<Object>> relatedData = new HashMap<>();

            createSheet(workbook, sheetName, data, clazz, relatedData);

            for (Map.Entry<Class<?>, List<Object>> entry : relatedData.entrySet()) {
                boolean hasColumnClassName = entry.getKey().isAnnotationPresent(ColumnClassName.class);
                String name = hasColumnClassName
                        ? entry.getKey().getAnnotation(ColumnClassName.class).value()
                        : entry.getKey().getSimpleName();

                createSheet(workbook, name, entry.getValue(), entry.getKey());
            }

            workbook.write(outputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error generating Excel file", e);
        }
    }

    private <E> void createSheet(Workbook workbook, String sheetName, List<E> data, Class<?> clazz)
            throws IllegalAccessException, NoSuchFieldException, SecurityException {
        createSheet(workbook, sheetName, data, clazz, new HashMap<>());
    }

    private <E> void createSheet(Workbook workbook, String sheetName, List<E> data, Class<?> clazz,
            Map<Class<?>, List<Object>> relatedData) {
        Sheet sheet = workbook.createSheet(sheetName);
        Field[] fields = clazz.getDeclaredFields();

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(ColumnPropertyName.class)) {
                ColumnPropertyName annotation = field.getAnnotation(ColumnPropertyName.class);
                headerRow.createCell(i).setCellValue(annotation.value());
            } else {
                headerRow.createCell(i).setCellValue(field.getName());
            }
        }

        try {
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i + 1);
                E item = data.get(i);

                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    field.setAccessible(true);

                    Object value = field.get(item);
                    if (value != null) {
                        if (value.getClass().isAnnotationPresent(ColumnClassName.class)) {
                            relatedData.computeIfAbsent(value.getClass(), k -> new ArrayList<>()).add(value);

                            Field idField = value.getClass().getDeclaredField("id");
                            idField.setAccessible(true);
                            row.createCell(j).setCellValue(String.valueOf(idField.get(value)));
                        } else if (value instanceof List<?>) {
                            row.createCell(j).setCellValue("[...]");
                        } else {
                            row.createCell(j).setCellValue(value.toString());
                        }
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }

            for (int i = 0; i < fields.length; i++) {
                sheet.autoSizeColumn(i);
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            log.error("Error generating Excel file", e);

            throw new RuntimeException("Error generating Excel file", e);
        }
    }
}
