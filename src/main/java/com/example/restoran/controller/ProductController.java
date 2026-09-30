package com.example.restoran.controller;

import com.example.restoran.model.Product;
import com.example.restoran.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            // Faylların eyni adlı olub üst-üstə düşməməsi üçün unikal ad yaradırıq
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("./uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Faylı uploads/ qovluğuna kopyalayırıq
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Məhsula şəkil yolunu təyin edirik
            product.setImageUrl("/uploads/" + fileName);
        }

        productRepository.save(product);
        return "redirect:/menu";
    }
}