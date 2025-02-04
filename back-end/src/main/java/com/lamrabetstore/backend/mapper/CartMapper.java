package com.lamrabetstore.backend.mapper;

import com.lamrabetstore.backend.dto.CartDTO;
import com.lamrabetstore.backend.dto.CartItemDTO;
import com.lamrabetstore.backend.model.Cart;
import com.lamrabetstore.backend.model.CartItem;
import com.lamrabetstore.backend.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDTO toDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setItems(cart.getItems().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    public CartItemDTO toDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setProductId(cartItem.getProduct().getId());
        return dto;
    }

    public CartItem toEntity(CartItemDTO dto, Cart cart, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setId(dto.getId());
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        return cartItem;
    }
}