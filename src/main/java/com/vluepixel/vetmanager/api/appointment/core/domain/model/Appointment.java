package com.vluepixel.vetmanager.api.appointment.core.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import com.vluepixel.vetmanager.api.appointment.details.domain.model.AppointmentDetails;
import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Appointment entity.
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
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime startAt;
    @Column(columnDefinition = "text")
    private String description;
    @OneToMany(mappedBy = "appointment", orphanRemoval = true)
    private List<AppointmentDetails> details;
    // TODO: Patient
    @CreatedBy
    @ManyToOne
    @NotAudited
    private User createdBy;

    @Builder.Default
    private boolean deleted = false;
}
