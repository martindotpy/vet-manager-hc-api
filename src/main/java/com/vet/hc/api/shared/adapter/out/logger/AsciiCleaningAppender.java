package com.vet.hc.api.shared.adapter.out.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;

/**
 * Appender that cleans ASCII color codes from log messages before writing them
 * to the log file.
 */
public class AsciiCleaningAppender extends RollingFileAppender<ILoggingEvent> {
    private String regexPattern = "\\u001B\\[[;\\d]*m";

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (eventObject == null) {
            return;
        }

        String cleanedMessage = eventObject.getFormattedMessage().replaceAll(regexPattern, "");
        ILoggingEvent cleanedEvent = new CleanedLoggingEvent(eventObject, cleanedMessage);

        super.append(cleanedEvent);
    }
}
