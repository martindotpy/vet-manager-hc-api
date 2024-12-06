package com.vet.hc.api.bill.treatmentsale.adapter.in.request;

import com.vet.hc.api.bill.treatmentsale.domain.payload.UpdateTreatmentSalePayload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to update a treatmentSale.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateTreatmentSaleRequest implements UpdateTreatmentSalePayload {
    @NotNull(message = "El id es requerido")
    @Min(value = 1, message = "El id no puede ser menor a 1")
    private Long id;

    @NotNull(message = "El precio total no puede ser nulo")
    @DecimalMin(value = "0.0", message = "El precio total no puede ser menor a 0")
    private Double price;
    @NotNull(message = "El descuento no puede ser nulo")
    @Min(value = 0, message = "El descuento no puede ser menor a 0")
    @Max(value = 100, message = "El descuento no puede ser mayor a 100")
    private Integer discount;

    @NotNull(message = "El id del tratamiento es requerido")
    @Min(value = 1, message = "El id del tratamiento no puede ser menor a 1")
    private Long treatmentId;
}
