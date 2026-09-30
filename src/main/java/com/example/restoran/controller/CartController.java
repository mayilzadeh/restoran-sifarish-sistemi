package com.example.restoran.controller;

import com.example.restoran.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "q", required = false) String q,
                            RedirectAttributes redirectAttributes) {
        cartService.addProduct(productId);
        redirectAttributes.addFlashAttribute("successMessage", "Məhsul səbətə əlavə olundu!");
        if (q != null && !q.isBlank()) {
            redirectAttributes.addAttribute("q", q);
        }
        return "redirect:/menu#product-" + productId;
    }

    @PostMapping("/increase")
    public String increase(@RequestParam("productId") Long productId) {
        cartService.increase(productId);
        return "redirect:/cart";
    }

    @PostMapping("/decrease")
    public String decrease(@RequestParam("productId") Long productId) {
        cartService.decrease(productId);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") Long productId) {
        cartService.removeProduct(productId);
        return "redirect:/cart";
    }
}