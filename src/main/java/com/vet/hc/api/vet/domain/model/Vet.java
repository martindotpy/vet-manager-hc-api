package com.vet.hc.api.vet.domain.model;

import com.vet.hc.api.user.domain.model.User;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a vet.
 */
@Getter
@Builder
public class Vet {
    private Long id;

    private User user;
}
