package com.lamrabetstore.backend.mapper;

import com.lamrabetstore.backend.dto.OrderDTO;
import com.lamrabetstore.backend.dto.OrderItemDTO;
import com.lamrabetstore.backend.model.Order;
import com.lamrabetstore.backend.model.OrderItem;
import com.lamrabetstore.backend.model.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus().name());
        dto.setPaymentMethod(order.getPaymentMethod().name());
        dto.setItems(order.getItems().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    public OrderItemDTO toDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setProductId(orderItem.getProduct().getId());
        return dto;
    }

    public OrderItem toEntity(OrderItemDTO dto, Order order, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        orderItem.setPrice(product.getPrice());
        return orderItem;
    }
}
