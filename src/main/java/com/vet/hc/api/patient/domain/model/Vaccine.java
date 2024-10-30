package com.vet.hc.api.patient.domain.model;

import java.time.LocalDateTime;

import com.vet.hc.api.sales.domain.model.Sale;
import com.vet.hc.api.user.domain.model.User;

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

    private Patient patient;
    private User vaccinatedBy;
    private Sale sale;
}
