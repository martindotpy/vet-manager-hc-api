package com.vet.hc.api.client.core.adapter.out.persistence.entity;

import java.util.Set;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.vet.hc.api.client.core.domain.enums.IdentificationType;
import com.vet.hc.api.client.email.adapter.out.persistence.entity.ClientEmailEntity;
import com.vet.hc.api.client.phone.adapter.out.persistence.entity.ClientPhoneEntity;

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
 * Client entity.
 */
@Entity
@Table(name = "client")
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedClientFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedClientFilter", condition = "deleted = :isDeleted")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder.Default
    private boolean deleted = false;
}
