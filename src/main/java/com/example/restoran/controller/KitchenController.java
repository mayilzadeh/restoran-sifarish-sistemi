package com.example.restoran.controller;

import com.example.restoran.model.Order;
import com.example.restoran.model.OrderStatus;
import com.example.restoran.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/kitchen")
@RequiredArgsConstructor
public class KitchenController {

    private final OrderService orderService;

    // GET /kitchen : gözləyən və hazırlanan sifarişlər
    @GetMapping
    public String getOrders(Model model) {
        List<Order> orders = new ArrayList<>(orderService.findByStatus(OrderStatus.PENDING));
        orders.addAll(orderService.findByStatus(OrderStatus.IN_PROGRESS));
        orders.sort(Comparator.comparing(Order::getCreatedAt,
                Comparator.nullsLast(Comparator.naturalOrder())));
        model.addAttribute("orders", orders);
        return "kitchen/orders";
    }

    // POST /kitchen/{id}/status : "Hazırlanır" və ya "Hazır"
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable("id") Long id,
                               @RequestParam("status") OrderStatus status) {
        if (status == OrderStatus.IN_PROGRESS || status == OrderStatus.READY) {
            orderService.updateStatus(id, status);
        }
        return "redirect:/kitchen";
    }
}