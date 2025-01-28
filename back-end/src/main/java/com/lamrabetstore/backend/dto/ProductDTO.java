package com.lamrabetstore.backend.dto;


import com.lamrabetstore.backend.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotNull(message = "Stock quantity is required")
    @Positive(message = "Stock quantity must be a positive value")
    private Integer stockQuantity;

    private String status;

    private String image;

    private CategoryDTO category;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}