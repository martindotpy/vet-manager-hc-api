package com.vluepixel.vetmanager.api.shared.domain.validation;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * Represents a external payload validator provider.
 */
@Slf4j
public final class ExternalPayloadValidatorProvider {
    private static ExternalPayloadValidator externalValidation;

    public static void set(ExternalPayloadValidator externalValidation) {
        Objects.requireNonNull(externalValidation, "External payload validator cannot be null");

        if (ExternalPayloadValidatorProvider.externalValidation != null) {
            log.warn("External payload validator already set: {}",
                    fgBrightRed(ExternalPayloadValidatorProvider.externalValidation.getClass().getName()));

            throw new IllegalStateException("External payload validator already set");
        }

        log.info("Setting external payload validator: {}",
                fgBrightGreen(externalValidation.getClass().getName()));

        ExternalPayloadValidatorProvider.externalValidation = externalValidation;
    }

    public static ExternalPayloadValidator get() {
        return externalValidation;
    }
}
