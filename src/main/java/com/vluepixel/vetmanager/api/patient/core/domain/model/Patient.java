package com.vluepixel.vetmanager.api.patient.core.domain.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;

import com.vluepixel.vetmanager.api.client.core.domain.model.Client;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vluepixel.vetmanager.api.patient.core.domain.enums.PatientGender;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vluepixel.vetmanager.api.patient.race.domain.model.Race;
import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;
import com.vluepixel.vetmanager.api.vaccine.core.domain.model.Vaccine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Patient.
 */
@Entity
@Audited
@SQLDelete(sql = "UPDATE patient SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpanishName("Paciente")
public final class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Long id;

    @Size(max = 50)
    @NotBlank
    @Column(columnDefinition = "varchar(50)")
    @SpanishName("Nombre")
    private String name;
    @NotNull
    @SpanishName("Fecha de nacimiento")
    private LocalDate birthDate;
    @Column(columnDefinition = "tinyint unsigned")
    @SpanishName("Edad")
    private Integer age;
    @NotNull
    @Enumerated(EnumType.STRING)
    @SpanishName("Género")
    private PatientGender gender;
    @Column(columnDefinition = "text")
    @SpanishName("Características")
    private String characteristics;
    @Builder.Default
    @SpanishName("Fallecido")
    private boolean deceased = false;
    @OneToMany(mappedBy = "patient")
    @SpanishName("Historias clínicas")
    private List<@NotNull MedicalHistory> histories;
    @OneToMany(mappedBy = "patient")
    @SpanishName("Registros médicos")
    private List<@NotNull MedicalRecord> records;
    @OneToMany(mappedBy = "patient")
    @SpanishName("Vacunas")
    private List<@NotNull Vaccine> vaccines;

    @NotNull
    @ManyToOne
    @SpanishName("Raza")
    private Race race;
    @NotNull
    @ManyToOne
    @SpanishName("Dueño")
    private Client owner;

    @Builder.Default
    private boolean deleted = false;
}
