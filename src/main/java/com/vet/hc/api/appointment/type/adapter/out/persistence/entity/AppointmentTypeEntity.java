package com.vet.hc.api.appointment.type.adapter.out.persistence.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Appointment type entity.
 *
 * @see com.vet.hc.api.appointment.type.domain.model.AppointmentType
 */
@Entity
@Table(name = "appointment_type", uniqueConstraints = {
        @UniqueConstraint(name = "UK_APPOINTMENT_TYPE_NAME", columnNames = { "name" })
})
@SQLDelete(sql = "UPDATE appointment_type SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedAppointmentTypeFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedAppointmentTypeFilter", condition = "deleted = :isDeleted")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(12)", nullable = false)
    private String name;
    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Integer durationInMinutes;
    @Column(columnDefinition = "DECIMAL(5, 2)", nullable = false)
    private Double price;

    @Builder.Default
    private boolean deleted = false;
}
