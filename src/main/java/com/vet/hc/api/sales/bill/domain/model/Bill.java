package com.vet.hc.api.sales.bill.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a bill.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    private Long id;

    private Double total;
    private Double discount;
    private Double totalPaid;
    private boolean isPaid;
    private LocalDateTime lastPaidDatetime;
    private LocalDateTime createdAt;
}
