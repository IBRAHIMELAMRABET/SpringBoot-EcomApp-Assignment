package com.lamrabetstore.backend.service;

import com.lamrabetstore.backend.dto.OrderDTO;
import com.lamrabetstore.backend.dto.OrderItemDTO;
import com.lamrabetstore.backend.exception.ResourceNotFoundException;
import com.lamrabetstore.backend.mapper.OrderMapper;
import com.lamrabetstore.backend.model.*;
import com.lamrabetstore.backend.model.enums.OrderStatus;
import com.lamrabetstore.backend.model.enums.PaymentMethod;
import com.lamrabetstore.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDTO placeOrder(String paymentMethod) {
        User user = getCurrentUser();
        Cart cart = cartService.getCartEntity();

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot place order with empty cart");
        }

        PaymentMethod paymentMethodEnum = PaymentMethod.valueOf(paymentMethod);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(paymentMethodEnum);

        double total = 0.0;
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        total = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        order.setTotal(total);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        cartService.emptyCart();

        return orderMapper.toDTO(savedOrder);
    }

    public Page<OrderDTO> getMyOrders(Pageable pageable) {
        User user = getCurrentUser();
        return orderRepository.findByUserId(user.getId(), pageable)
                .map(orderMapper::toDTO);
    }

    public OrderDTO getOrder(Long orderId) {
        User user = getCurrentUser();
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Can only cancel pending orders");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toDTO(orderRepository.save(order));
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}