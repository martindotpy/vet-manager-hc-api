package com.vet.hc.api.user.core.adapter.out.logger;

import java.util.List;
import java.util.Map;

import org.slf4j.Marker;
import org.slf4j.event.KeyValuePair;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;

public class CleanedLoggingEvent implements ILoggingEvent {
    private final ILoggingEvent originalEvent;
    private final String cleanedMessage;

    public CleanedLoggingEvent(ILoggingEvent originalEvent, String cleanedMessage) {
        this.originalEvent = originalEvent;
        this.cleanedMessage = cleanedMessage;
    }

    @Override
    public String getThreadName() {
        return originalEvent.getThreadName();
    }

    @Override
    public Level getLevel() {
        return originalEvent.getLevel();
    }

    @Override
    public String getMessage() {
        return cleanedMessage;
    }

    @Override
    public Object[] getArgumentArray() {
        return originalEvent.getArgumentArray();
    }

    @Override
    public String getFormattedMessage() {
        return cleanedMessage;
    }

    @Override
    public String getLoggerName() {
        return originalEvent.getLoggerName();
    }

    @Override
    public LoggerContextVO getLoggerContextVO() {
        return originalEvent.getLoggerContextVO();
    }

    @Override
    public IThrowableProxy getThrowableProxy() {
        return originalEvent.getThrowableProxy();
    }

    @Override
    public StackTraceElement[] getCallerData() {
        return originalEvent.getCallerData();
    }

    @Override
    public boolean hasCallerData() {
        return originalEvent.hasCallerData();
    }

    @Override
    public Marker getMarker() {
        return originalEvent.getMarker();
    }

    @Override
    public Map<String, String> getMDCPropertyMap() {
        return originalEvent.getMDCPropertyMap();
    }

    @Override
    public Map<String, String> getMdc() {
        return originalEvent.getMdc();
    }

    @Override
    public long getTimeStamp() {
        return originalEvent.getTimeStamp();
    }

    @Override
    public void prepareForDeferredProcessing() {
        originalEvent.prepareForDeferredProcessing();
    }

    @Override
    public List<Marker> getMarkerList() {
        return originalEvent.getMarkerList();
    }

    @Override
    public int getNanoseconds() {
        return originalEvent.getNanoseconds();
    }

    @Override
    public long getSequenceNumber() {
        return originalEvent.getSequenceNumber();
    }

    @Override
    public List<KeyValuePair> getKeyValuePairs() {
        return originalEvent.getKeyValuePairs();
    }
}
