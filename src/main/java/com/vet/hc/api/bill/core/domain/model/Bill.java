package com.vet.hc.api.bill.core.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale;
import com.vet.hc.api.bill.productsale.domain.model.ProductSale;
import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.user.core.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a bill.
 *
 * <p>
 * The bill is the document that represents the payment of the services and
 * products that the client has acquired.
 * </p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Bill {
    private Long id;

    private Double total;
    private Integer discount;
    private Double totalPaid;
    private boolean paid;
    private LocalDateTime lastPaidDateTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User createdBy;
    private User updatedBy;
    private Client client;
    private List<AppointmentSale> appointmentSales;
    private List<TreatmentSale> treatmentSales;
    private List<ProductSale> productSales;
}