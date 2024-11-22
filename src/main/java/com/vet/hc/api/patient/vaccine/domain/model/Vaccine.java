package com.vet.hc.api.patient.vaccine.domain.model;

import java.time.LocalDateTime;

import com.vet.hc.api.user.core.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a vaccine.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {
    private Long id;

    private String name;
    private Integer dose;
    private LocalDateTime providedAt;

    private User vaccinator;
}
