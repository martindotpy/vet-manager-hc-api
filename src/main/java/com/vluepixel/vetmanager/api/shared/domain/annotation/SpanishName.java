package com.vluepixel.vetmanager.api.shared.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spanish name annotation.
 */
@Documented
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SpanishName {
    String value();
}
