package com.example.restoran.repository;

import com.example.restoran.model.Order;
import com.example.restoran.model.OrderStatus;
import com.example.restoran.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Spring Security / User üçün lazım olan sorğular:
    List<Order> findByStatus(OrderStatus status);

    List<Order> findByUser(User user); // Qırmızı olan metod budur!
}