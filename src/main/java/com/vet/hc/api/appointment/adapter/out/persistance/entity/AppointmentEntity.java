package com.vet.hc.api.appointment.adapter.out.persistance.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.vet.hc.api.user.adapter.out.persistance.model.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appointment")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime startAt;
    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String description;

    // TODO: Bill entity
    // TODO: Patient entity
    private AppointmentDetailsEntity details;
    private UserEntity vet;
}
