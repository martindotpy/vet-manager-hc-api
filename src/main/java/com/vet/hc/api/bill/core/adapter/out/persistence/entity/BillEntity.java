package com.vet.hc.api.bill.core.adapter.out.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import com.vet.hc.api.bill.appointmentsale.adapter.out.persistence.entity.AppointmentSaleEntity;
import com.vet.hc.api.bill.productsale.adapter.out.persistence.entity.ProductSaleEntity;
import com.vet.hc.api.bill.treatmentsale.adapter.out.persistence.entity.TreatmentSaleEntity;
import com.vet.hc.api.client.core.adapter.out.persistence.entity.ClientEntity;
import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;

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

@Entity
@Table(name = "bill")
@SQLDelete(sql = "UPDATE bill SET deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "deletedBillFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedBillFilter", condition = "deleted = :isDeleted")
public class BillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total;
    private Integer discount;
    private Double totalPaid;
    // @Column(name = "is_paid")
    private boolean paid;
    private LocalDateTime lastPaidDateTime;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity createdBy;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity updatedBy;
    @ManyToOne(fetch = FetchType.EAGER)
    private ClientEntity client;
    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
    private List<AppointmentSaleEntity> appointmentSales;
    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
    private List<TreatmentSaleEntity> treatmentSales;
    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
    private List<ProductSaleEntity> productSales;

    @Builder.Default
    private boolean deleted = false;
}
