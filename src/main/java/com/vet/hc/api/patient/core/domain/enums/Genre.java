package com.vet.hc.api.patient.core.domain.enums;

import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

/**
 * Represents the genre of a patient.
 */
public enum Genre {
    @ColumnPropertyName("Masculino")
    MALE,
    @ColumnPropertyName("Femenino")
    FEMALE,
    @ColumnPropertyName("No especificado")
    NOT_SPECIFIED;
}
