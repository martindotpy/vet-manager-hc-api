package com.vet.hc.api.appointment.details.adapter.out.persistence.entity;

import com.vet.hc.api.appointment.core.adapter.out.persistence.entity.AppointmentEntity;
import com.vet.hc.api.appointment.type.adapter.out.persistence.entity.AppointmentTypeEntity;

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

@Entity
@Table(name = "appointment_details")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "SMALLINT UNSIGNED", nullable = false)
    private Integer durationInMinutes;
    @Column(columnDefinition = "DECIMAL(6, 2)", nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private AppointmentTypeEntity type;
    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointment;
}
