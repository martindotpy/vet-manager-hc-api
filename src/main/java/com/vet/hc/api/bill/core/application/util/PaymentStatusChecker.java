package com.vet.hc.api.bill.core.application.util;

public class PaymentStatusChecker {
    public static Double applyDiscount(Double total, Integer discount) {
        return total - (total * discount / 100);
    }

    public static boolean isPaid(Double totalApplied, Double totalPaid) {
        return totalApplied.equals(totalPaid);
    }

    public static boolean hasIncreasedTotalPaid(Double totalPaid, Double totalPaidBefore) {
        return totalPaid > totalPaidBefore;
    }
}
