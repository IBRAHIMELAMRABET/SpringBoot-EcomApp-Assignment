package com.lamrabetstore.backend.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${coinbase.api.key}")
    private String coinbaseApiKey;

    @Value("${coinbase.webhook.secret}")
    private String coinbaseWebhookSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }
}
