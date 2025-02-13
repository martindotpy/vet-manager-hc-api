package com.vluepixel.vetmanager.api.shared.domain.repository;

import com.vluepixel.vetmanager.api.shared.domain.exception.ErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.RepositoryException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;

/**
 * Repository exception handler.
 */
public interface RepositoryExceptionHandler {
    /**
     * Handle repository exception.
     *
     * @param e the exception
     * @throws ErrorException               the specific error exception
     * @throws ValidationException          if the exception is a validation error
     * @throws InternalServerErrorException if the exception is not handled
     */
    void handle(RepositoryException e);
}
