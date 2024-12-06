package com.vet.hc.api.bill.treatmentsale.adapter.in.request;

import com.vet.hc.api.bill.treatmentsale.domain.payload.CreateTreatmentSalePayload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to create a new treatmentSale.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateTreatmentSaleRequest implements CreateTreatmentSalePayload {
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
    @NotNull(message = "El id del vendedor es requerido")
    @Min(value = 1, message = "El id del vendedor no puede ser menor a 1")
    private Long sellerId;
    @NotNull(message = "El id de la cuenta es requerido")
    @Min(value = 1, message = "El id de la cuenta no puede ser menor a 1")
    private Long billId;
}
