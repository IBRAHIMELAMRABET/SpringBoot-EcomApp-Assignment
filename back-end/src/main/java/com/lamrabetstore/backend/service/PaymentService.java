package com.lamrabetstore.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamrabetstore.backend.dto.OrderDTO;
import com.lamrabetstore.backend.dto.payment.*;
import com.lamrabetstore.backend.model.Order;
import com.lamrabetstore.backend.model.enums.OrderStatus;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderService orderService;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${coinbase.api.key}")
    private String coinbaseApiKey;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${stripe.webhook.secret}")
    private String stripeWebhookSecret;

    @Transactional
    public StripePaymentResponse createStripePayment(PaymentRequestDTO paymentRequest) {
        try {
            OrderDTO order = orderService.getOrder(paymentRequest.getOrderId());

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (order.getTotal() * 100))
                    .setCurrency(paymentRequest.getCurrency().toLowerCase())
                    .setDescription("Order #" + order.getId())
                    .putMetadata("orderId", order.getId().toString())
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods
                                    .builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            orderService.updateOrderStatus(order.getId(), OrderStatus.PAYMENT_PENDING);

            StripePaymentResponse response = new StripePaymentResponse();
            response.setClientSecret(paymentIntent.getClientSecret());
            response.setPaymentIntentId(paymentIntent.getId());
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error creating Stripe payment", e);
        }
    }

    @Transactional
    public CoinbasePaymentResponse createCoinbasePayment(PaymentRequestDTO paymentRequest) {
        try {
            OrderDTO order = orderService.getOrder(paymentRequest.getOrderId());

            String chargeUrl = "https://api.commerce.coinbase.com/charges";
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-CC-Api-Key", coinbaseApiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> chargeRequest = new HashMap<>();
            chargeRequest.put("name", "Order #" + order.getId());
            chargeRequest.put("description", "Payment for order #" + order.getId());
            chargeRequest.put("pricing_type", "fixed_price");
            chargeRequest.put("local_price", Map.of(
                    "amount", order.getTotal(),
                    "currency", paymentRequest.getCurrency()
            ));
            chargeRequest.put("metadata", Map.of("orderId", order.getId()));
            chargeRequest.put("redirect_url", frontendUrl + "/payment/success");
            chargeRequest.put("cancel_url", frontendUrl + "/payment/cancel");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(chargeRequest, headers);
            ResponseEntity<Map> response = new RestTemplate().postForEntity(chargeUrl, requestEntity, Map.class);

            orderService.updateOrderStatus(order.getId(), OrderStatus.PAYMENT_PENDING);

            CoinbasePaymentResponse paymentResponse = new CoinbasePaymentResponse();
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            paymentResponse.setChargeUrl((String) data.get("hosted_url"));
            paymentResponse.setChargeId((String) data.get("id"));
            return paymentResponse;

        } catch (Exception e) {
            throw new RuntimeException("Error creating Coinbase payment", e);
        }
    }

    @Transactional
    public void handleStripeWebhook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, stripeWebhookSecret);

            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
                String orderId = paymentIntent.getMetadata().get("orderId");
                orderService.updateOrderStatus(Long.parseLong(orderId), OrderStatus.PAID);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error processing Stripe webhook", e);
        }
    }

    @Transactional
    public void handleCoinbaseWebhook(String payload, String sigHeader) {
        try {
            // Verify signature
            // Implementation depends on Coinbase's webhook verification method

            ObjectMapper mapper = new ObjectMapper();
            JsonNode event = mapper.readTree(payload);

            if ("charge:confirmed".equals(event.get("type").asText())) {
                String orderId = event.get("data").get("metadata").get("orderId").asText();
                orderService.updateOrderStatus(Long.parseLong(orderId), OrderStatus.PAID);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error processing Coinbase webhook", e);
        }
    }
}