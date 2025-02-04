package com.lamrabetstore.backend.controller;

import com.lamrabetstore.backend.dto.payment.CoinbasePaymentResponse;
import com.lamrabetstore.backend.dto.payment.PaymentRequestDTO;
import com.lamrabetstore.backend.dto.payment.StripePaymentResponse;
import com.lamrabetstore.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/stripe/create")
    public ResponseEntity<StripePaymentResponse> createStripePayment(
            @Valid @RequestBody PaymentRequestDTO paymentRequest) {
        return ResponseEntity.ok(paymentService.createStripePayment(paymentRequest));
    }

    @PostMapping("/coinbase/create")
    public ResponseEntity<CoinbasePaymentResponse> createCoinbasePayment(
            @Valid @RequestBody PaymentRequestDTO paymentRequest) {
        return ResponseEntity.ok(paymentService.createCoinbasePayment(paymentRequest));
    }

    @PostMapping("/stripe/webhook")
    public ResponseEntity<Void> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        paymentService.handleStripeWebhook(payload, sigHeader);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/coinbase/webhook")
    public ResponseEntity<Void> handleCoinbaseWebhook(
            @RequestBody String payload,
            @RequestHeader("X-CC-Webhook-Signature") String sigHeader) {
        paymentService.handleCoinbaseWebhook(payload, sigHeader);
        return ResponseEntity.ok().build();
    }
}