package com.example.restoran.controller;

import com.example.restoran.model.Category;
import com.example.restoran.model.Product;
import com.example.restoran.repository.CategoryRepository;
import com.example.restoran.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @GetMapping({"/", "/menu"})
    public String showMenu(@RequestParam(value = "q", required = false) String q, Model model) {

        List<Product> allProducts = productRepository.findAll();
        List<Product> products = allProducts;

        // Axtarış: ad və təsvir üzrə süzür
        if (q != null && !q.isBlank()) {
            String query = normalize(q.trim());
            products = allProducts.stream()
                    .filter(p -> normalize(p.getName() + " "
                            + (p.getDescription() == null ? "" : p.getDescription()))
                            .contains(query))
                    .toList();
        }

        // Yalnız məhsulu olan kateqoriyaları göstər
        Set<Long> categoryIds = products.stream()
                .filter(p -> p.getCategory() != null)
                .map(p -> p.getCategory().getId())
                .collect(Collectors.toSet());

        List<Category> categories = categoryRepository.findAll().stream()
                .filter(c -> categoryIds.contains(c.getId()))
                .toList();

        // Axtarış qutusunda çıxan təkliflər üçün bütün məhsul adları
        List<String> suggestions = allProducts.stream()
                .map(Product::getName)
                .distinct()
                .sorted()
                .toList();

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("q", q);
        return "menu";
    }

    // "cay" yazanda "Çay" da tapılsın deyə Azərbaycan hərflərini sadələşdirir
    private String normalize(String text) {
        return text.toLowerCase(Locale.forLanguageTag("az"))
                .replace('ə', 'e').replace('ı', 'i').replace('ö', 'o')
                .replace('ü', 'u').replace('ş', 's').replace('ç', 'c')
                .replace('ğ', 'g');
    }
}