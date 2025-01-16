package com.vet.hc.api.patient.species.adapter.out.persistence.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

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
        @UniqueConstraint(name = "UK_SPECIES_NAME", columnNames = { "name" })
})
@SQLDelete(sql = "UPDATE species SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedSpeciesFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedSpeciesFilter", condition = "deleted = :isDeleted")
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

    @Builder.Default
    private boolean deleted = false;
}
