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
import jakarta.persistence.FetchType;
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
 * Client entity.
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
    private Long id;

    @Size(max = 50)
    @NotBlank
    @Column(columnDefinition = "varchar(50)")
    private String firstName;
    @Size(max = 50)
    @NotBlank
    @Column(columnDefinition = "varchar(50)")
    private String lastName;
    @Size(max = 12)
    @NotBlank
    @Column(columnDefinition = "varchar(12)")
    private String identification;
    @Enumerated(EnumType.STRING)
    private IdentificationType identificationType;
    @Size(max = 125)
    @NotBlank
    @Column(columnDefinition = "varchar(125)")
    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(columnDefinition = "varchar(50)")
    private List<@Size(max = 50) @NotBlank String> emails;
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(columnDefinition = "varchar(15)")
    private List<@Size(max = 15) @NotBlank String> phones;

    @Builder.Default
    private boolean deleted = false;
}
