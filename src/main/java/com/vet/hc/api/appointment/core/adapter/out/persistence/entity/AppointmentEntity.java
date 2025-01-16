package com.vet.hc.api.appointment.core.adapter.out.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.vet.hc.api.appointment.details.adapter.out.persistence.entity.AppointmentDetailsEntity;
import com.vet.hc.api.patient.core.adapter.out.persistence.entity.PatientEntity;
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

/**
 * Appointment entity.
 */
@Entity
@Table(name = "appointment")
@SQLDelete(sql = "UPDATE appointment SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedAppointmentFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedAppointmentFilter", condition = "deleted = :isDeleted")
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
    @Column(nullable = false)
    private LocalDateTime startAt;
    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private PatientEntity patient;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appointment")
    private List<AppointmentDetailsEntity> details;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity vet;

    @Builder.Default
    private boolean deleted = false;
}
