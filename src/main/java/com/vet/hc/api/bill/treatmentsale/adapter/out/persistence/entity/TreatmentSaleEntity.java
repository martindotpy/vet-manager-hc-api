package com.vet.hc.api.bill.treatmentsale.adapter.out.persistence.entity;

import com.vet.hc.api.bill.core.adapter.out.persistence.entity.BillEntity;
import com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence.entity.TreatmentEntity;
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
 * Treatment sale entity.
 */
@Entity
@Table(name = "treatment_sale")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentSaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Integer discount;

    @ManyToOne(fetch = FetchType.EAGER)
    private TreatmentEntity treatment;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity seller;
    @ManyToOne
    private BillEntity bill;
}
