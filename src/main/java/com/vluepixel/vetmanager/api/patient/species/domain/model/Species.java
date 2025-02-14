package com.vluepixel.vetmanager.api.patient.species.domain.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;

import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Species entity.
 */
@Entity
@Audited
@SQLDelete(sql = "UPDATE species SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpanishName("Especies")
public final class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "tinyint unsigned")
    private Integer id;

    @Size(max = 50)
    @NotBlank
    @Column(columnDefinition = "varchar(50)")
    private String name;
}
