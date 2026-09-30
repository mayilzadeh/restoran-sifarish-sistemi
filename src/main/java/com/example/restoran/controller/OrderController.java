package com.example.restoran.controller;

import com.example.restoran.model.Order;
import com.example.restoran.model.User;
import com.example.restoran.repository.UserRepository;
import com.example.restoran.service.CartService;
import com.example.restoran.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final CartService cartService;

    @PostMapping("/place")
    public String placeOrder(Principal principal) {
        if (cartService.isEmpty()) {
            return "redirect:/cart";
        }
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));
        Order order = orderService.placeOrder(user);
        return "redirect:/order/success/" + order.getId();
    }

    @GetMapping("/success/{id}")
    public String success(@PathVariable("id") Long id, Model model, Principal principal) {
        Order order = orderService.findById(id);

        // Başqa istifadəçinin sifarişini görməyin qarşısını alır
        if (!order.getUser().getUsername().equals(principal.getName())) {
            return "redirect:/menu";
        }

        model.addAttribute("order", order);
        return "order-success";
    }

    @GetMapping("/my-orders")
    public String myOrders(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));
        model.addAttribute("orders", orderService.findByUser(user));
        return "my-orders";
    }
}