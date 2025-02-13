package com.vluepixel.vetmanager.api.auth.core.adapter.out.exception;

/**
 * Get user when do not logged in exception.
 */
public final class GetUserWhenDoNotLoggedInException extends RuntimeException {
    public GetUserWhenDoNotLoggedInException() {
        super("User not logged in");
    }
}
