package com.vet.hc.api.shared.domain.query;

import static com.vet.hc.api.shared.domain.util.CaseConverterUtil.toCamelCase;

import lombok.Value;

/**
 * Field update.
 */
@Value
public final class FieldUpdate {
    private final String field;
    private final Object value;

    /**
     * Set field.
     *
     * <p>
     * The field name will be converted to camel case.
     * </p>
     *
     * @param field Field.
     * @param value Value.
     * @return Field update.
     */
    public static FieldUpdate set(String field, Object value) {
        field = toCamelCase(field);

        return new FieldUpdate(field, value);
    }
}
