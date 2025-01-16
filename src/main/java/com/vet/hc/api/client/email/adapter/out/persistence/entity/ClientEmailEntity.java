package com.vet.hc.api.client.email.adapter.out.persistence.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.vet.hc.api.client.core.adapter.out.persistence.entity.ClientEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Client email entity.
 */
@Entity
@Table(name = "client_email")
@SQLDelete(sql = "UPDATE client_email SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedClientEmailFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedClientEmailFilter", condition = "deleted = :isDeleted")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @Builder.Default
    private boolean deleted = false;
}
