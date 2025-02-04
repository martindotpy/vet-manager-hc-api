package com.vet.hc.api.shared.adapter.in.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Regex constants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexConstants {
    /**
     * Image URL regex.
     */
    public static final String IMAGE_URL_REGEX = "^https://"
            + "([a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}" // Domain
            + "(:\\d{1,5})?" // Optional port
            + "(/[^\\s?#]*)" // Route
            + "(\\.(?:jpg|jpeg|png|webp))?" // Optional image extension
            + "(\\?[^\\s#]*)?" // Optional parameters
            + "$";

    /**
     * URL regex. It allows only HTTPS URLs.
     */
    public static final String URL_REGEX = "^https://"
            + "([a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}" // Domain
            + "(:\\d{1,5})?" // Port optional
            + "(/[^\\s?#]*)?" // Route optional
            + "(\\?[^\\s#]*)?" // Additional parameters optional
            + "(#\\S*)?" // Anchor optional
            + "$";
}
