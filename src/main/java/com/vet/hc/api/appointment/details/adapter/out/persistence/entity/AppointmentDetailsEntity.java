package com.vet.hc.api.appointment.details.adapter.out.persistence.entity;

import java.util.List;

import com.vet.hc.api.appointment.type.adapter.out.persistence.entity.AppointmentTypeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany
    @JoinTable(name = "appointment_details_types", joinColumns = @JoinColumn(name = "appointment_details_id"), inverseJoinColumns = @JoinColumn(name = "appointment_type_id"))
    private List<AppointmentTypeEntity> types;
}
