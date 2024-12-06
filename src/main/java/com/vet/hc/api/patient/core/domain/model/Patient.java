package com.vet.hc.api.patient.core.domain.model;

import java.time.LocalDate;
import java.util.List;

import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.patient.vaccine.domain.model.Vaccine;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;

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
@ColumnClassName("Paciente")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Patient {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Nombre")
    private String name;
    @ColumnPropertyName("Fecha de nacimiento")
    private LocalDate birthDate;
    @ColumnPropertyName("Edad")
    private Integer age;
    @ColumnPropertyName("Características")
    private String characteristics;
    @ColumnPropertyName("Fallecido")
    private boolean deceased;

    @ColumnPropertyName("Género")
    private Genre genre;
    @ColumnPropertyName("Dueño")
    private Client owner;
    @ColumnPropertyName("Raza")
    private Race race;
    @ColumnPropertyName("Vacunas")
    private List<Vaccine> vaccines;
    @ColumnPropertyName("Historias clínicas")
    private List<MedicalHistory> medicalHistories;
    @ColumnPropertyName("Historias médicas")
    private List<MedicalRecord> medicalRecords;
}