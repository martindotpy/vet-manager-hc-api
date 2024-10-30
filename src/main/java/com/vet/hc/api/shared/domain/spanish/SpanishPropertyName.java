package com.vet.hc.api.shared.domain.spanish;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be used in the fields of the domain classes to indicate that
 * the name of the field is in Spanish.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpanishPropertyName {
    String value();
}
