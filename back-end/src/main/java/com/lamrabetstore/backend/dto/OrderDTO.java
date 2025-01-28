package com.lamrabetstore.backend.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;

    @NotNull(message = "Total is required")
    private Double total;

    @NotNull(message = "Status is required")
    private String status;

    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    @NotNull(message = "Items are required")
    private List<OrderItemDTO> items;
}