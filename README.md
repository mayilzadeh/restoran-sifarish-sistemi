# Restoran Sifariş Sistemi

Spring Boot + Spring MVC + Spring Security + JPA/Hibernate + Thymeleaf ilə hazırlanmış restoran sifariş sistemi.
Hamburg IT Academy kurs işi.

## Texnologiyalar
- Java 17, Spring Boot 3.x
- Spring Security (BCrypt, 3 rol)
- Spring Data JPA / Hibernate
- Thymeleaf
- MySQL (və ya H2)

## İşə salma
1. MySQL-də verilənlər bazası yaradın: `CREATE DATABASE restoran_db;`
2. `src/main/resources/application.properties` faylında istifadəçi adı və parolu yazın.
3. Layihəni işə salın:
   `./gradlew bootRun`
4. Brauzerdə açın: http://localhost:8080

## Test hesabları
| Rol | İstifadəçi | Parol |
|---|---|---|
| ADMIN | admin | admin123 |
| USER | user | user123 |
| KITCHEN | kitchen | kitchen123 |

## Funksionallıq
- **USER:** menyu, səbət (miqdarı artır/azalt), sifariş ver, sifariş tarixçəsi
- **KITCHEN:** gözləyən sifarişlər, "Hazırlanır" və "Hazır" statusları
- **ADMIN:** məhsul, kateqoriya və sifariş idarəetməsi, şəkil yükləmə

## Verilənlər bazası
users, categories, products, orders, order_items
(SQL dump: `database/restoran_db.sql`)