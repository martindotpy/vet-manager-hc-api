package com.vluepixel.vetmanager.api.shared.domain.validation;

import java.util.List;

/**
 * Validation interface.
 */
public interface Validation {
    List<ValidationError> validate();
}
