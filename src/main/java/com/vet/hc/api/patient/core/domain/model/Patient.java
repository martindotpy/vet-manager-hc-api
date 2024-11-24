package com.vet.hc.api.patient.core.domain.model;

import java.time.LocalDate;
import java.util.List;

import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.patient.vaccine.domain.model.Vaccine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a patient.
 *
 * <p>
 * The patient is the animal that is being treated by the veterinarian. In other
 * words, it is the pet of the client.
 * </p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Patient {
    private Long id;

    private String name;
    private LocalDate birthDate;
    private Integer age;
    private String characteristics;
    private boolean deceased;

    private Genre genre;
    private Client owner;
    private Race race;
    private List<Vaccine> vaccines;
    // private List<MedicalHistory> medicalHistories;
}