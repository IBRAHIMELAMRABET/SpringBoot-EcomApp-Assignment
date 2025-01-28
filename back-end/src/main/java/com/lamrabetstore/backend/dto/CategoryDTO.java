package com.lamrabetstore.backend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private String image;
}