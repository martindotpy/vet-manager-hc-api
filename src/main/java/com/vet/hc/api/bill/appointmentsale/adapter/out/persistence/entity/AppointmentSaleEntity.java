package com.vet.hc.api.bill.appointmentsale.adapter.out.persistence.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

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
@SQLDelete(sql = "UPDATE appointment_sale SET deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "deletedAppointmentSaleFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedAppointmentSaleFilter", condition = "deleted = :isDeleted")
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

    @Builder.Default
    private boolean deleted = false;
}
