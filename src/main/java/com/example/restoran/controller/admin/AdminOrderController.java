package com.example.restoran.controller.admin;

import com.example.restoran.model.OrderStatus;
import com.example.restoran.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public String list(@RequestParam(value = "status", required = false) OrderStatus status, Model model) {
        if (status != null) {
            model.addAttribute("orders", orderService.findByStatus(status));
            model.addAttribute("selectedStatus", status);
        } else {
            model.addAttribute("orders", orderService.findAll());
        }
        model.addAttribute("statuses", OrderStatus.values());
        return "admin/orders/list";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable("id") Long id, @RequestParam("status") OrderStatus status) {
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders";
    }
}