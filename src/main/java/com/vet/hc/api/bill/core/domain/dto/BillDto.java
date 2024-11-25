package com.vet.hc.api.bill.core.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.client.core.domain.dto.ClientDto;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BillDto {
    private Long id;

    private Double total;
    private Integer discount;
    private Double totalPaid;
    private boolean paid;
    private LocalDateTime lastPaidDateTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserDto createdBy;
    private UserDto updatedBy;
    private ClientDto client;
    private List<AppointmentSaleDto> appointmentSales;
    private List<TreatmentSaleDto> treatmentSales;
    private List<ProductSaleDto> productSales;
}
