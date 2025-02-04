package com.lamrabetstore.backend.controller;


import com.lamrabetstore.backend.dto.CartDTO;
import com.lamrabetstore.backend.dto.CartItemDTO;
import com.lamrabetstore.backend.model.Cart;
import com.lamrabetstore.backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@Valid @RequestBody CartItemDTO itemDTO) {
        return ResponseEntity.ok(cartService.addToCart(itemDTO));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartDTO> updateCartItem(
            @PathVariable Long itemId,
            @Valid @RequestBody CartItemDTO itemDTO) {
        return ResponseEntity.ok(cartService.updateCartItem(itemId, itemDTO));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartDTO> deleteCartItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.deleteCartItem(itemId));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<CartDTO> emptyCart() {
        return ResponseEntity.ok(cartService.emptyCart());
    }
}