package com.vet.hc.api.bill.core.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale;
import com.vet.hc.api.bill.productsale.domain.model.ProductSale;
import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;
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
@ColumnClassName("Factura")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Bill {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Total")
    private Double total;
    @ColumnPropertyName("Descuento")
    private Integer discount;
    @ColumnPropertyName("Total pagado")
    private Double totalPaid;
    @ColumnPropertyName("Pagado")
    private boolean paid;
    @ColumnPropertyName("Fecha de pago")
    private LocalDateTime lastPaidDateTime;
    @ColumnPropertyName("Fecha de creación")
    private LocalDateTime createdAt;
    @ColumnPropertyName("Fecha de actualización")
    private LocalDateTime updatedAt;

    @ColumnPropertyName("Creado por")
    private User createdBy;
    @ColumnPropertyName("Actualizado por")
    private User updatedBy;
    @ColumnPropertyName("Cliente")
    private Client client;
    @ColumnPropertyName("Ventas de citas médicas")
    private List<AppointmentSale> appointmentSales;
    @ColumnPropertyName("Ventas de tratamiento")
    private List<TreatmentSale> treatmentSales;
    @ColumnPropertyName("Ventas de productos")
    private List<ProductSale> productSales;
}