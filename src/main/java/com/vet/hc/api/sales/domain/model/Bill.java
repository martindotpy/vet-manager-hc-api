package com.vet.hc.api.sales.domain.model;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a bill.
 */
@Getter
@Builder
public class Bill {
    private Long id;

    private Double total;
    private Double discount;
    private Double totalPaid;
    private boolean isPaid;
    private Instant lastPaidDatetime;
    private Instant createdAt;
}
