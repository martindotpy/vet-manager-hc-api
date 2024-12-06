package com.vet.hc.api.bill.core.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.client.core.domain.dto.ClientDto;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the bill.
 */
@ColumnClassName("Factura")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BillDto {
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
    private UserDto createdBy;
    @ColumnPropertyName("Actualizado por")
    private UserDto updatedBy;
    @ColumnPropertyName("Cliente")
    private ClientDto client;
    @ColumnPropertyName("Detalles de la cita")
    private List<AppointmentSaleDto> appointmentSales;
    @ColumnPropertyName("Detalles de los tratamientos")
    private List<TreatmentSaleDto> treatmentSales;
    @ColumnPropertyName("Detalles de los productos")
    private List<ProductSaleDto> productSales;
}
