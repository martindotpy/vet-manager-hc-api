package com.vluepixel.vetmanager.api.shared.application.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

/**
 * Indicates that annotated class is a <em>RestControllerAdapter</em>.
 *
 * <p>
 * Represents a rest controller adapter component. This annotation serves as a
 * specialization of Spring's {@link @RestControllerAdapter}, allowing for
 * implementation classes to be auto-detected through classpath scanning by
 * Spring.
 * </p>
 *
 * <p>
 * Rest controller adapters are the classes that expose endpoints to the
 * application's clients.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface RestControllerAdapter {
    @AliasFor(annotation = RestController.class)
    String value() default "";
}
