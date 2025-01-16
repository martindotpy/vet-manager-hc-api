package com.vet.hc.api.patient.vaccine.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.vet.hc.api.patient.core.adapter.out.persistence.entity.PatientEntity;
import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

/**
 * Vaccine entity.
 */
@Entity
@Table(name = "vaccine")
@SQLDelete(sql = "UPDATE vaccine SET deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "deletedVaccineFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedVaccineFilter", condition = "deleted = :isDeleted")
public class VaccineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer dose;
    private LocalDateTime vaccinatedAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PatientEntity patient;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity vaccinator;
    // @Nullable
    // private ProductSaleEntity product;
    @Builder.Default
    private boolean deleted = false;
}
