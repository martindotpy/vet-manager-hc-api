package com.vet.hc.api.user.core.adapter.out.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;

public class AsciiCleaningAppender extends RollingFileAppender<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent eventObject) {
        if (eventObject == null) {
            return;
        }

        // Limpia caracteres no deseados
        String cleanedMessage = eventObject.getFormattedMessage().replaceAll("\\u001B\\[[;\\d]*m", "");

        // Crea un nuevo evento con el mensaje limpiado
        ILoggingEvent cleanedEvent = new CleanedLoggingEvent(eventObject, cleanedMessage);

        super.append(cleanedEvent);
    }
}
