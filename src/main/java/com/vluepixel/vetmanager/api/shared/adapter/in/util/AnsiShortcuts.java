package com.vluepixel.vetmanager.api.shared.adapter.in.util;

import static org.fusesource.jansi.Ansi.ansi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Shortcut methods for ANSI colors.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AnsiShortcuts {
    public static String fgBrightBlack(Object value) {
        return ansi().fgBrightBlack().a(value).reset().toString();
    }

    public static String fgBrightBlue(Object value) {
        return ansi().fgBrightBlue().a(value).reset().toString();
    }

    public static String fgBrightCyan(Object value) {
        return ansi().fgBrightCyan().a(value).reset().toString();
    }

    public static String fgBrightDefault(Object value) {
        return ansi().fgBrightDefault().a(value).reset().toString();
    }

    public static String fgBrightGreen(Object value) {
        return ansi().fgBrightGreen().a(value).reset().toString();
    }

    public static String fgBrightMagenta(Object value) {
        return ansi().fgBrightMagenta().a(value).reset().toString();
    }

    public static String fgBrightRed(Object value) {
        return ansi().fgBrightRed().a(value).reset().toString();
    }

    public static String fgBrightYellow(Object value) {
        return ansi().fgBrightYellow().a(value).reset().toString();
    }
}
