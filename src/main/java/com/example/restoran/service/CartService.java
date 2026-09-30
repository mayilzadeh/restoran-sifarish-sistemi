package com.example.restoran.service;

import com.example.restoran.model.CartItem;
import com.example.restoran.model.Product;
import com.example.restoran.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final List<CartItem> items = new ArrayList<>();

    public void addProduct(Long productId) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Məhsul tapılmadı"));
        items.add(new CartItem(product, 1));
    }

    public void increase(Long productId) {
        items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(i.getQuantity() + 1));
    }

    // Miqdar 1-dirsə, məhsul səbətdən çıxarılır
    public void decrease(Long productId) {
        items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(i -> {
                    if (i.getQuantity() <= 1) {
                        items.remove(i);
                    } else {
                        i.setQuantity(i.getQuantity() - 1);
                    }
                });
    }

    public void removeProduct(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public List<CartItem> getItems() {
        return items;
    }

    // OrderService bu metodu istifadə edir
    public List<CartItem> getLines() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        items.clear();
    }
}