package com.vet.hc.api.patient.vaccine.domain.model;

import java.time.LocalDateTime;

import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.annotation.Nullable;
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
    private LocalDateTime vaccinatedAt;

    @Nullable
    private Patient patient;
    private User vaccinator;
}
