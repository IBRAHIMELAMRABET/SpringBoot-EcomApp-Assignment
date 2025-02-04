package com.lamrabetstore.backend.service;


import com.lamrabetstore.backend.dto.CartDTO;
import com.lamrabetstore.backend.dto.CartItemDTO;
import com.lamrabetstore.backend.exception.ResourceNotFoundException;
import com.lamrabetstore.backend.mapper.CartMapper;
import com.lamrabetstore.backend.model.Cart;
import com.lamrabetstore.backend.model.CartItem;
import com.lamrabetstore.backend.model.Product;
import com.lamrabetstore.backend.model.User;
import com.lamrabetstore.backend.repository.CartItemRepository;
import com.lamrabetstore.backend.repository.CartRepository;
import com.lamrabetstore.backend.repository.ProductRepository;
import com.lamrabetstore.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    public CartDTO getCart() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return cartMapper.toDTO(cart);
    }

    @Transactional
    public CartDTO addToCart(CartItemDTO itemDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(itemDTO.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + itemDTO.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = cartMapper.toEntity(itemDTO, cart, product);
            cart.getItems().add(cartItemRepository.save(cartItem));
        }

        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Transactional
    public CartDTO updateCartItem(Long itemId, CartItemDTO itemDTO) {
        Cart cart = getCartEntity();
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new ResourceNotFoundException("Cart item not found in user's cart");
        }

        Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        item.setQuantity(itemDTO.getQuantity());
        item.setProduct(product);
        cartItemRepository.save(item);

        return cartMapper.toDTO(getCartEntity());
    }

    @Transactional
    public CartDTO deleteCartItem(Long itemId) {
        Cart cart = getCartEntity();
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new ResourceNotFoundException("Cart item not found in user's cart");
        }

        cartItemRepository.delete(item);
        return cartMapper.toDTO(getCartEntity());
    }

    @Transactional
    public CartDTO emptyCart() {
        Cart cart = getCartEntity();
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    public Cart getCartEntity() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }
}