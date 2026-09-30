package com.example.restoran.config;

import com.example.restoran.model.Category;
import com.example.restoran.model.Product;
import com.example.restoran.model.Role;
import com.example.restoran.model.User;
import com.example.restoran.repository.CategoryRepository;
import com.example.restoran.repository.ProductRepository;
import com.example.restoran.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {

        // 1. İstifadəçiləri yaradırıq (əgər yoxdursa)
        if (userRepo.count() == 0) {
            userRepo.save(User.builder().username("admin").password(passwordEncoder.encode("admin123")).role(Role.ROLE_ADMIN).build());
            userRepo.save(User.builder().username("user").password(passwordEncoder.encode("user123")).role(Role.ROLE_USER).build());
            userRepo.save(User.builder().username("kitchen").password(passwordEncoder.encode("kitchen123")).role(Role.ROLE_KITCHEN).build());
            System.out.println("✅ İstifadəçilər yaradıldı!");
        }

        // Məlumat artıq varsa, təkrar yaratma (admin paneldən əlavə olunanlar silinməsin)
        if (categoryRepo.count() > 0) {
            return;
        }

        // 3. Kateqoriyaları dəqiq sıralama ilə yaradırıq (Öncə Pastalar, Pizzalar, İçkilər, Şirniyyatlar, Çaylar)
        Category pasta = categoryRepo.save(Category.builder().name("Pastalar").build());
        Category pizza = categoryRepo.save(Category.builder().name("Pizzalar").build());
        Category drinks = categoryRepo.save(Category.builder().name("İçkilər").build());
        Category desserts = categoryRepo.save(Category.builder().name("Şirniyyatlar").build());
        Category teas = categoryRepo.save(Category.builder().name("Çaylar").build());

        // 4. Məhsulları yaradırıq və kateqoriyalara bağlayırıq

        // Pizzalar
        productRepo.save(Product.builder().name("Margarita Pizza").price(new BigDecimal("23.00")).description("Mozzarella, Pizza sousu, Reyhan")
                .category(pizza).imageUrl("/images/margarita.jpg").build());
        productRepo.save(Product.builder().name("Pepperoni Pizza").price(new BigDecimal("24.50")).description("Kolbasa, Mozzarella, Yaşıl reyhan, Pizza sousu")
                .category(pizza).imageUrl("/images/pepperoni.jpg").build());
        productRepo.save(Product.builder().name("Beef Pizza").price(new BigDecimal("26.00")).description("Hisə verilmiş dana əti, Suda Mozarella, Parmezan, Rukola, Pomidor sousu, Pizza sousu")
                .category(pizza).imageUrl("/images/beef.jpg").build());
        productRepo.save(Product.builder().name("4 Cheese Pizza").price(new BigDecimal("26.00")).description("Mozarella, Rokfor, Parmezan, Hisə verilmiş pendir, Pendir sousu")
                .category(pizza).imageUrl("/images/4cheese.jpg").build());

        // İçkilər
        productRepo.save(Product.builder().name("Coca-Cola / Zero").price(new BigDecimal("5.00")).description("0.33l")
                .category(drinks).imageUrl("/images/cola.jpg").build());
        productRepo.save(Product.builder().name("Fanta").price(new BigDecimal("5.00")).description("0.33l")
                .category(drinks).imageUrl("/images/fanta.jpg").build());
        productRepo.save(Product.builder().name("Sprite").price(new BigDecimal("5.00")).description("0.33l")
                .category(drinks).imageUrl("/images/sprite.jpg").build());
        productRepo.save(Product.builder().name("Redbull").price(new BigDecimal("9.00")).description("0.25l")
                .category(drinks).imageUrl("/images/redbull.jpg").build());

        // Şirniyyatlar
        productRepo.save(Product.builder().name("Profiterol").price(new BigDecimal("17.00"))
                .category(desserts).imageUrl("/images/Profiterol.jpg").build());
        productRepo.save(Product.builder().name("San Sebastian Cheesecake").price(new BigDecimal("15.00"))
                .category(desserts).imageUrl("/images/Cheesecake.jpg").build());
        productRepo.save(Product.builder().name("Honey Cake").price(new BigDecimal("14.00"))
                .category(desserts).imageUrl("/images/honeycake.jpg").build());
        productRepo.save(Product.builder().name("Lemon Cheesecake").price(new BigDecimal("15.00"))
                .category(desserts).imageUrl("/images/Lemon Cheesecake.jpg").build());

        // Çaylar
        productRepo.save(Product.builder().name("Qara Çay").price(new BigDecimal("14.00"))
                .category(teas).imageUrl("/images/garachay.jpg").build());
        productRepo.save(Product.builder().name("Yaşıl Çay").price(new BigDecimal("15.00"))
                .category(teas).imageUrl("/images/yashilchay.jpg").build());
        productRepo.save(Product.builder().name("Yasəmən").price(new BigDecimal("15.00"))
                .category(teas).imageUrl("/images/yasemen.jpg").build());
        productRepo.save(Product.builder().name("Zəncəfilli Portağal Çayı").price(new BigDecimal("16.00"))
                .category(teas).imageUrl("/images/zencefilli.jpg").build());

        // Pastalar
        productRepo.save(Product.builder().name("Spaghetti Bolognese").description("Spagetti, neopolitana sous, parmezan, Dana əti, Göyərti")
                .price(new BigDecimal("22.00")).category(pasta).imageUrl("/images/spagetti.jpg").build());
        productRepo.save(Product.builder().name("Penne All Arrabiata").description("Penne, Pomidor çerri, Zeytun, Pesto sousu, Sarımsaq, Parmezan, Acılı neopolitan sousu")
                .price(new BigDecimal("23.00")).category(pasta).imageUrl("/images/penne.jpg").build());
        productRepo.save(Product.builder().name("Alfredo Tagliatelle").description("Tagliatelli, Toyuq filesi, Göbələk, Qaymaq, Sarımsaq, Reyhan, Parmezan")
                .price(new BigDecimal("24.00")).category(pasta).imageUrl("/images/alfredo.jpg").build());
        productRepo.save(Product.builder().name("Spaghetti Burrata").description("Spaghetti, Burrata, Qaymaq, Pesto sous, Çerri pomidor, Pramezan")
                .price(new BigDecimal("28.00")).category(pasta).imageUrl("/images/burrata.jpg").build());

        System.out.println("✅ Kateqoriya və məhsullar uğurla yeniləndi və yaradıldı!");
    }
}