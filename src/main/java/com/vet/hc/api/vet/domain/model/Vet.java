package com.vet.hc.api.vet.domain.model;

import com.vet.hc.api.user.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a vet.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vet {
    private Long id;

    private User user;
}
