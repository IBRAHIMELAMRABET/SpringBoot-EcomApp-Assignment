package com.lamrabetstore.backend.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CartDTO {
    private Long id;

    @NotNull(message = "Items are required")
    private List<CartItemDTO> items;
}
