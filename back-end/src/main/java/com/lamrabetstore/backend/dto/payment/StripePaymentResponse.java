package com.lamrabetstore.backend.dto.payment;

import lombok.Data;

@Data
public class StripePaymentResponse {
    private String clientSecret;
    private String paymentIntentId;

}