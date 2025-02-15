package com.vluepixel.vetmanager.api.client.core.domain.model;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;

import com.vluepixel.vetmanager.api.client.core.domain.enums.IdentificationType;
import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * Client.
 */
@Entity
@Audited
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpanishName("Cliente")
public final class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Long id;

    @Size(max = 50)
    @NotBlank
    @Column(columnDefinition = "varchar(50)")
    @SpanishName("Nombre")
    private String firstName;
    @Size(max = 50)
    @NotBlank
    @Column(columnDefinition = "varchar(50)")
    @SpanishName("Apellido")
    private String lastName;
    @Size(max = 12)
    @NotBlank
    @Column(columnDefinition = "varchar(12)")
    @SpanishName("Identificación")
    private String identification;
    @Enumerated(EnumType.STRING)
    @SpanishName("Tipo de identificación")
    private IdentificationType identificationType;
    @Size(max = 125)
    @NotBlank
    @Column(columnDefinition = "varchar(125)")
    @SpanishName("Dirección")
    private String address;

    @ElementCollection
    @Column(columnDefinition = "varchar(50)")
    @SpanishName("Correos electrónicos")
    private List<@Size(max = 50) @NotBlank String> emails;
    @ElementCollection
    @Column(columnDefinition = "varchar(15)")
    @SpanishName("Teléfonos")
    private List<@Size(max = 15) @NotBlank String> phones;

    @Builder.Default
    private boolean deleted = false;
}
