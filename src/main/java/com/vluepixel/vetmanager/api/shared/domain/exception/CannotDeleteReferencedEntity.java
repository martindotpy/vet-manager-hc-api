package com.vluepixel.vetmanager.api.shared.domain.exception;

import static com.vluepixel.vetmanager.api.shared.domain.util.SpanishUtils.getName;

import lombok.Getter;

/**
 * Can not delete referenced entity exception.
 */
@Getter
public final class CannotDeleteReferencedEntity extends ErrorException {
    private String message = "No se puede eliminar el/la % porque está en uso en otros registros";
    private final int status = 409;

    public CannotDeleteReferencedEntity(Class<?> clazz) {
        String name = getName(clazz);
        this.message = this.message.replace("%", name.substring(0, 1).toLowerCase() + name.substring(1));
    }
}
