package com.vet.hc.api.bill.core.adapter.out.persistence.entity;

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
 * @see com.vet.hc.api.appointment.type.domain.model.Bill
 */
@Entity
@Table(name = "appointment_type", uniqueConstraints = {
        @UniqueConstraint(name = "UK_APPOINTMENT_TYPE_NAME", columnNames = { "name" })
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(12)", nullable = false)
    private String name;
    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Integer durationInMinutes;
    @Column(columnDefinition = "DECIMAL(5, 2)", nullable = false)
    private Double price;
}
