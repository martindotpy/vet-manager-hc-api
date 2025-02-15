package com.vluepixel.vetmanager.api.appointment.core.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import com.vluepixel.vetmanager.api.appointment.details.domain.model.AppointmentDetails;
import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Appointment.
 */
@Entity
@Audited
@SQLDelete(sql = "UPDATE appointment SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpanishName("Cita")
public final class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Long id;

    @CreatedDate
    @SpanishName("Creado en")
    private LocalDateTime createdAt;
    @NotNull
    @SpanishName("Fecha de inicio")
    private LocalDateTime startAt;
    @Column(columnDefinition = "text")
    @SpanishName("Descripción")
    private String description;
    @OneToMany(cascade = { CascadeType.REMOVE })
    @JoinTable(name = "appointment_appointment_details", joinColumns = @JoinColumn(name = "appointment_id"), inverseJoinColumns = @JoinColumn(name = "details_id"))
    @SpanishName("Detalles")
    private List<AppointmentDetails> details;
    @NotNull
    @ManyToOne
    @SpanishName("Paciente")
    private Patient patient;
    @CreatedBy
    @ManyToOne
    @SpanishName("Creado por")
    private User createdBy;

    @Builder.Default
    private boolean deleted = false;
}
