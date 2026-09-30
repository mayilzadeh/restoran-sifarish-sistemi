package com.example.restoran.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // 👈 Builder xətasını həll edən əsas anotasiya
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private String imageUrl; // 👈 Şəkil üçün əlavə etdiyimiz sahə

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}