package com.example.restoran.config;

import com.example.restoran.model.CartItem;
import com.example.restoran.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final CartService cartService;

    @ModelAttribute("cartCount")
    public int cartCount() {
        return cartService.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}