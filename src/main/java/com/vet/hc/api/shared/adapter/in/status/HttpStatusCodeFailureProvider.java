package com.vet.hc.api.shared.adapter.in.status;

import static com.google.common.base.Preconditions.checkState;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.extern.slf4j.Slf4j;

/**
 * Provider for HTTP status codes for failures.
 */
@Slf4j
public final class HttpStatusCodeFailureProvider {
    private static final Map<Class<? extends Failure>, HttpStatusCodeFailureHandler<? extends Failure>> httpStatusCodeFailureHandlers = new HashMap<>();

    static {
        log.info("Initializing HTTP status code failure handlers");
        initializeHandlers();
        log.info("HTTP status code failure handlers initialized");
    }

    private HttpStatusCodeFailureProvider() {
    }

    /**
     * Initializes the handlers.
     */
    private static void initializeHandlers() {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackages("com.vet.hc.api")
                        .filterInputsBy(new FilterBuilder()
                                .includePackage("com.vet.hc.api")
                                .excludePackage("com.vet.hc.api.shared")));

        @SuppressWarnings("rawtypes")
        Set<Class<? extends HttpStatusCodeFailureHandler>> handlers = reflections
                .getSubTypesOf(HttpStatusCodeFailureHandler.class);

        for (@SuppressWarnings("rawtypes")
        Class<? extends HttpStatusCodeFailureHandler> handlerClass : handlers) {
            try {
                HttpStatusCodeFailureHandler<?> handler = handlerClass.getDeclaredConstructor().newInstance();
                Class<? extends Failure> failureClass = getFailureType(handlerClass);

                if (failureClass != null) {
                    addHttpStatusCodeFailureHandler(failureClass, handler);
                }
            } catch (Exception e) {
                throw new IllegalStateException("Error initializing handler: " + handlerClass.getName(), e);
            }
        }
    }

    /**
     * Gets the failure type from the handler class.
     *
     * @param handlerClass The handler class.
     * @return The failure type.
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Failure> getFailureType(Class<?> handlerClass) {
        return (Class<? extends Failure>) ((ParameterizedType) handlerClass.getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }

    /**
     * Adds a HTTP status code failure handler.
     *
     * @param failureClass The failure class.
     * @param handler      The handler.
     */
    private static void addHttpStatusCodeFailureHandler(
            Class<? extends Failure> failureClass,
            HttpStatusCodeFailureHandler<?> handler) {
        log.info("Adding HTTP status code failure handler for failure: {}", failureClass.getName());
        httpStatusCodeFailureHandlers.put(failureClass, handler);
    }

    /**
     * Gets the HTTP status code for a failure.
     *
     * @param failure The failure.
     * @param <T>     The failure type.
     * @return The HTTP status code.
     */
    public static <T extends Failure> int get(T failure) {
        @SuppressWarnings("unchecked")
        HttpStatusCodeFailureHandler<T> handler = (HttpStatusCodeFailureHandler<T>) httpStatusCodeFailureHandlers
                .get(failure.getClass());

        checkState(handler != null, "No handler found for failure: " + failure.getClass().getName());

        return handler.getHttpStatusCode(failure);
    }
}