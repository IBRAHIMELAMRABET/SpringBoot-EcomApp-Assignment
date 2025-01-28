package com.lamrabetstore.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be a positive value")
    private Integer quantity;

    @NotNull(message = "Product ID is required")
    private Long productId;
}