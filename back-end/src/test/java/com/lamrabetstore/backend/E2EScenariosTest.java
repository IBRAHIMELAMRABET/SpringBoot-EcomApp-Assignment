package com.lamrabetstore.backend;

import com.lamrabetstore.backend.dto.*;
import com.lamrabetstore.backend.dto.payment.PaymentRequestDTO;
import com.lamrabetstore.backend.dto.payment.StripePaymentResponse;
import com.lamrabetstore.backend.model.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:13:///testdb",
        "spring.datasource.username=testuser",
        "spring.datasource.password=testpass"
})
public class E2EScenariosTest extends AbstractE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void endToEndUserFlow() {
        // Step 1: Register a new user
        RegistrationRequestDto registrationRequest = new RegistrationRequestDto();
        registrationRequest.setEmail("testuser@test.com");
        registrationRequest.setPassword("password");
        registrationRequest.setEmail("test@example.com");

        ResponseEntity<RegistrationResponseDto> registerResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/auth/register",
                registrationRequest,
                RegistrationResponseDto.class
        );
        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Step 2: Login with the registered user
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("testuser@test.com");
        authRequest.setPassword("password");

        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/auth/login",
                authRequest,
                AuthResponse.class
        );
        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = loginResponse.getBody().getToken();

        // Step 3: Update user profile
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        UserDTO updatedUser = new UserDTO();
        updatedUser.setEmail("updated@example.com");

        ResponseEntity<UserDTO> updateProfileResponse = restTemplate.exchange(
                "http://localhost:" + port + "/users/me",
                HttpMethod.PUT,
                new HttpEntity<>(updatedUser, headers),
                UserDTO.class
        );
        assertThat(updateProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateProfileResponse.getBody().getEmail()).isEqualTo("updated@example.com");

        // Step 4: Browse products
        ResponseEntity<List<ProductDTO>> productsResponse = restTemplate.exchange(
                "http://localhost:" + port + "/products",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );
        assertThat(productsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productsResponse.getBody()).isNotEmpty();

        // Step 5: Add a product to the cart
        CartItemDTO cartItem = new CartItemDTO();
        cartItem.setProductId(1L);
        cartItem.setQuantity(2);

        ResponseEntity<CartDTO> addToCartResponse = restTemplate.exchange(
                "http://localhost:" + port + "/cart/add",
                HttpMethod.POST,
                new HttpEntity<>(cartItem, headers),
                CartDTO.class
        );
        assertThat(addToCartResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(addToCartResponse.getBody().getItems()).hasSize(1);

        // Step 6: Place an order
        ResponseEntity<OrderDTO> placeOrderResponse = restTemplate.exchange(
                "http://localhost:" + port + "/orders?paymentMethod=stripe",
                HttpMethod.POST,
                new HttpEntity<>(headers),
                OrderDTO.class
        );
        assertThat(placeOrderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(placeOrderResponse.getBody().getId()).isNotNull();

        Long orderId = placeOrderResponse.getBody().getId();

        // Step 7: Make a payment for the order
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setPaymentMethod("stripe");
        paymentRequest.setCurrency("USD");

        ResponseEntity<StripePaymentResponse> paymentResponse = restTemplate.exchange(
                "http://localhost:" + port + "/payment/stripe/create",
                HttpMethod.POST,
                new HttpEntity<>(paymentRequest, headers),
                StripePaymentResponse.class
        );
        assertThat(paymentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(paymentResponse.getBody().getPaymentIntentId()).isNotNull();

        // Step 8: Admin updates order status
        AuthRequest adminAuthRequest = new AuthRequest();
        adminAuthRequest.setEmail("admin@test.com");
        adminAuthRequest.setPassword("adminpassword");

        ResponseEntity<AuthResponse> adminLoginResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/auth/login",
                adminAuthRequest,
                AuthResponse.class
        );
        assertThat(adminLoginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String adminToken = adminLoginResponse.getBody().getToken();

        HttpHeaders adminHeaders = new HttpHeaders();
        adminHeaders.setBearerAuth(adminToken);

        // Update order status
        ResponseEntity<OrderDTO> updateOrderStatusResponse = restTemplate.exchange(
                "http://localhost:" + port + "/orders/" + orderId + "/status?status=SHIPPED",
                HttpMethod.PUT,
                new HttpEntity<>(adminHeaders),
                OrderDTO.class
        );
        assertThat(updateOrderStatusResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateOrderStatusResponse.getBody().getStatus()).isEqualTo(OrderStatus.SHIPPED);

        // Step 9: User cancels order
        ResponseEntity<OrderDTO> cancelOrderResponse = restTemplate.exchange(
                "http://localhost:" + port + "/orders/" + orderId + "/cancel",
                HttpMethod.POST,
                new HttpEntity<>(headers),
                OrderDTO.class
        );
        assertThat(cancelOrderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cancelOrderResponse.getBody().getStatus()).isEqualTo(OrderStatus.CANCELLED);

        // Step 10: Admin deletes a product
        ResponseEntity<Void> deleteProductResponse = restTemplate.exchange(
                "http://localhost:" + port + "/admin/products/1",
                HttpMethod.DELETE,
                new HttpEntity<>(adminHeaders),
                Void.class
        );
        assertThat(deleteProductResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}