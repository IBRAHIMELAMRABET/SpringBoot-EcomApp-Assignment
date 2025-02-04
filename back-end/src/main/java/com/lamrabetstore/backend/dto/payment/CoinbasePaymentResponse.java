package com.lamrabetstore.backend.dto.payment;


import lombok.Data;

@Data
public class CoinbasePaymentResponse {
    private String chargeUrl;
    private String chargeId;
}
