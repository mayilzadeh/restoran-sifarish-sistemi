package com.example.restoran.controller.admin;

import com.example.restoran.model.Product;
import com.example.restoran.repository.CategoryRepository;
import com.example.restoran.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // GET /admin/products : siyahı
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/products/list";
    }

    // GET /admin/products/new : əlavə etmə forması
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/products/form";
    }

    // POST /admin/products : əlavə et
    @PostMapping
    public String create(@RequestParam("name") String name,
                         @RequestParam("price") BigDecimal price,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam("categoryId") Long categoryId,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                         RedirectAttributes ra) throws IOException {
        Product product = new Product();
        fill(product, name, price, description, categoryId, imageFile);
        productRepository.save(product);
        ra.addFlashAttribute("message", "Məhsul əlavə olundu");
        return "redirect:/admin/products";
    }

    // GET /admin/products/{id}/edit : redaktə forması
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Məhsul tapılmadı: " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/products/form";
    }

    // POST /admin/products/{id} : redaktə et
    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("name") String name,
                         @RequestParam("price") BigDecimal price,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam("categoryId") Long categoryId,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                         RedirectAttributes ra) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Məhsul tapılmadı: " + id));
        fill(product, name, price, description, categoryId, imageFile);
        productRepository.save(product);
        ra.addFlashAttribute("message", "Məhsul yeniləndi");
        return "redirect:/admin/products";
    }

    // POST /admin/products/{id}/delete : sil
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            productRepository.deleteById(id);
            ra.addFlashAttribute("message", "Məhsul silindi");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "Bu məhsul sifarişlərdə istifadə olunub, silinə bilməz");
        }
        return "redirect:/admin/products";
    }

    // Sahələri doldurur; yeni şəkil seçilməyibsə köhnə şəkil qalır
    private void fill(Product product, String name, BigDecimal price, String description,
                      Long categoryId, MultipartFile imageFile) throws IOException {
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Kateqoriya tapılmadı")));

        if (imageFile != null && !imageFile.isEmpty()) {
            String original = imageFile.getOriginalFilename() == null ? "image" : imageFile.getOriginalFilename();
            String safeName = original.replaceAll("[^a-zA-Z0-9._-]", "_");
            String fileName = UUID.randomUUID() + "_" + safeName;

            Path uploadPath = Paths.get("./uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(imageFile.getInputStream(), uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);
            product.setImageUrl("/uploads/" + fileName);
        }
    }
}