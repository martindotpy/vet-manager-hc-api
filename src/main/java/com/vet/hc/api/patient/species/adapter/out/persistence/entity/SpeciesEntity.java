package com.vet.hc.api.patient.species.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Species entity.
 *
 * @see com.vet.hc.api.species..domain.model.Species
 */
@Entity
@Table(name = "species", uniqueConstraints = {
        @UniqueConstraint(name = "UK_SPECIE_NAME", columnNames = { "name" })
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(12)", nullable = false)
    private String name;
}
