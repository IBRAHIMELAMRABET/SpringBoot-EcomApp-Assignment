package com.lamrabetstore.backend.mapper;

import com.lamrabetstore.backend.dto.ProductDTO;
import com.lamrabetstore.backend.model.Product;
import com.lamrabetstore.backend.model.enums.ProductStatus;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setStatus(product.getStatus().name());
        productDTO.setImage(product.getImage());
        productDTO.setCategoryId(product.getCategory().getId());
        CategoryMapper categoryMapper = new CategoryMapper();
        productDTO.setCategory(categoryMapper.toDTO(product.getCategory()));
        return productDTO;
    }

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setStatus(ProductStatus.valueOf(productDTO.getStatus().toUpperCase()));
        product.setImage(productDTO.getImage());
        return product;
    }
}