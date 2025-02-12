package com.vluepixel.vetmanager.api.shared.domain.validation;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * Represents a external request validator provider.
 */
@Slf4j
public final class ExternalRequestValidatorProvider {
    private static ExternalRequestValidator externalValidation;

    public static void set(ExternalRequestValidator externalValidation) {
        Objects.requireNonNull(externalValidation, "External request validator cannot be null");

        if (ExternalRequestValidatorProvider.externalValidation != null) {
            log.warn("External request validator already set: {}",
                    fgBrightRed(ExternalRequestValidatorProvider.externalValidation.getClass().getName()));

            throw new IllegalStateException("External request validator already set");
        }

        log.info("Setting external request validator: {}",
                fgBrightGreen(externalValidation.getClass().getName()));

        ExternalRequestValidatorProvider.externalValidation = externalValidation;
    }

    public static ExternalRequestValidator get() {
        return externalValidation;
    }
}
