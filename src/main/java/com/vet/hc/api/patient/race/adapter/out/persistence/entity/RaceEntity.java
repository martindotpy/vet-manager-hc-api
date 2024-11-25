package com.vet.hc.api.patient.race.adapter.out.persistence.entity;

import com.vet.hc.api.patient.species.adapter.out.persistence.entity.SpeciesEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Race entity.
 *
 * @see com.vet.hc.api.race..domain.model.Race
 */
@Entity
@Table(name = "race", uniqueConstraints = {
        @UniqueConstraint(name = "UK_SPECIE_NAME", columnNames = { "name" })
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(12)", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private SpeciesEntity species;
}