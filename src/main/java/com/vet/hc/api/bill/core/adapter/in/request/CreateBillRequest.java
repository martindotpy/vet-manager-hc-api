package com.vet.hc.api.bill.core.adapter.in.request;

import com.vet.hc.api.bill.core.domain.payload.CreateBillPayload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Create bill dto (request).
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateBillRequest implements CreateBillPayload {
    @NotNull(message = "El precio total no puede ser nulo")
    @DecimalMin(value = "0.0", message = "El precio total no puede ser menor a 0")
    private Double total;
    @NotNull(message = "El descuento no puede ser nulo")
    @Min(value = 0, message = "El descuento no puede ser menor a 0")
    @Max(value = 100, message = "El descuento no puede ser mayor a 100")
    private Integer discount;
    @NotNull(message = "El total pagado no puede ser nulo")
    @DecimalMin(value = "0.0", message = "El total pagado no puede ser menor a 0")
    private Double totalPaid;

    @NotNull(message = "El id del cliente no puede ser nulo")
    @Min(value = 1, message = "El id del cliente no puede ser menor a 1")
    private Long clientId;
}
