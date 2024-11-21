package com.vet.hc.api.client.phone.adapter.out.persistence.entity;

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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_phone")
public class ClientPhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;
}
