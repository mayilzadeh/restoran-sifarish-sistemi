package com.example.restoran.controller.admin;

import com.example.restoran.model.Category;
import com.example.restoran.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("newCategory", new Category());
        return "admin/categories/list";
    }

    @PostMapping
    public String create(@ModelAttribute Category category, RedirectAttributes ra) {
        categoryService.save(category);
        ra.addFlashAttribute("message", "Kateqoriya əlavə olundu");
        return "redirect:/admin/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            categoryService.delete(id);
            ra.addFlashAttribute("message", "Kateqoriya silindi");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Kateqoriyanı silmək mümkün deyil (məhsulları var)");
        }
        return "redirect:/admin/categories";
    }
}