package com.vluepixel.vetmanager.api.patient.race.domain.model;

import org.hibernate.envers.Audited;

import com.vluepixel.vetmanager.api.patient.species.domain.model.Species;
import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Race entity.
 */
@Entity
@Audited
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpanishName("Raza")
public final class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "tinyint unsigned")
    private Integer id;

    @Size(max = 50)
    @NotBlank
    @Column(columnDefinition = "varchar(50)")
    private String name;

    @NotNull
    @ManyToOne
    private Species species;
}
