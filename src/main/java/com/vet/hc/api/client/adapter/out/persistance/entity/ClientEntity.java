package com.vet.hc.api.client.adapter.out.persistance.entity;

import java.util.Set;

import com.vet.hc.api.client.domain.enums.IdentificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity client.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String firstName;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String lastName;
    @Column(columnDefinition = "VARCHAR(12)", nullable = false)
    private String identification;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IdentificationType identificationType;
    @Column(columnDefinition = "VARCHAR(125)", nullable = false)
    private String address;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientEmailEntity> emails;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientPhoneEntity> phones;
}
