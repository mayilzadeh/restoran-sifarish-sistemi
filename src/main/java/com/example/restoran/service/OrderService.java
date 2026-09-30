package com.example.restoran.service;

import com.example.restoran.model.*;
import com.example.restoran.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Transactional
    public Order placeOrder(User user) {
        if (cartService.isEmpty()) {
            throw new IllegalStateException("Səbət boşdur");
        }

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .items(new ArrayList<>())
                .total(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem line : cartService.getLines()) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(line.getProduct())
                    .quantity(line.getQuantity())
                    .price(line.getProduct().getPrice())
                    .build();

            order.getItems().add(orderItem);
            total = total.add(line.getTotalPrice());
        }

        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);

        cartService.clear();

        return savedOrder;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sifariş tapılmadı: " + id));
    }

    @Transactional
    public void updateStatus(Long id, OrderStatus status) {
        Order order = findById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }
}