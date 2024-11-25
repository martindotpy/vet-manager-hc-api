package com.vet.hc.api.bill.appointmentsale.adapter.out.persistence.entity;

import com.vet.hc.api.appointment.core.adapter.out.persistence.entity.AppointmentEntity;
import com.vet.hc.api.bill.core.adapter.out.persistence.entity.BillEntity;
import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Appointment sale entity.
 */
@Entity
@Table(name = "appointment_sale")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Integer discount;

    @ManyToOne(fetch = FetchType.EAGER)
    private AppointmentEntity appointment;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity seller;
    @ManyToOne
    private BillEntity bill;
}
