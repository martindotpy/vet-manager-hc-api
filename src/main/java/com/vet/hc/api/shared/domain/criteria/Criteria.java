package com.vet.hc.api.shared.domain.criteria;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Criteria class.
 */
@Getter
@AllArgsConstructor
public final class Criteria {
    private final List<Filter> filters;
}
