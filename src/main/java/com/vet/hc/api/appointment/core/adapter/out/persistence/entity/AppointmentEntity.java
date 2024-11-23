package com.vet.hc.api.appointment.core.adapter.out.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.vet.hc.api.appointment.details.adapter.out.persistence.entity.AppointmentDetailsEntity;
import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String description;

    // TODO: PatientEntity
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appointment")
    private List<AppointmentDetailsEntity> details;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity vet;
}
