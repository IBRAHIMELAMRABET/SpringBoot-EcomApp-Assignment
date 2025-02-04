package com.lamrabetstore.backend.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    private String currency = "USD";
}