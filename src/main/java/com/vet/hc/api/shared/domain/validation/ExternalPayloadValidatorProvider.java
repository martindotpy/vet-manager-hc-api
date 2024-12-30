package com.vet.hc.api.shared.domain.validation;

import static org.fusesource.jansi.Ansi.ansi;

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
                    ansi()
                            .fgRed()
                            .a(ExternalPayloadValidatorProvider.externalValidation.getClass().getName())
                            .reset());

            throw new IllegalStateException("External payload validator already set");
        }

        log.info("Setting external payload validator: {}",
                ansi()
                        .fgBrightGreen()
                        .a(externalValidation.getClass().getName())
                        .reset());

        ExternalPayloadValidatorProvider.externalValidation = externalValidation;
    }

    public static ExternalPayloadValidator get() {
        return externalValidation;
    }
}
