package com.vet.hc.api.user.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserFailure implements Failure {
    NOT_FOUND("User not found"),
    EMAIL_ALREADY_IN_USE("Email already in use"),
    UNEXPECTED("Unexpected error");

    private final String message;
}
