-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: restoran_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (149,'Pastalar'),(150,'Pizzalar'),(151,'İçkilər'),(152,'Şirniyyatlar'),(153,'Çaylar'),(154,'Şorba');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` decimal(38,2) NOT NULL,
  `quantity` int NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,23.00,2,1,580),(2,14.00,1,2,575),(3,22.00,1,3,579),(4,5.00,1,3,567),(5,22.00,1,4,579),(6,24.50,1,4,564),(7,5.00,1,4,568),(8,9.00,1,4,570),(9,22.00,1,5,579),(10,23.00,1,5,580),(11,22.00,1,6,579),(12,23.00,1,6,580),(13,24.00,1,6,581),(14,28.00,1,6,582);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `status` enum('DELIVERED','IN_PROGRESS','PENDING','READY') NOT NULL,
  `total` decimal(38,2) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2026-09-24 19:19:12.435127','IN_PROGRESS',46.00,1),(2,'2026-09-24 19:19:56.586768','READY',14.00,1),(3,'2026-09-24 19:22:50.536087','READY',27.00,1),(4,'2026-09-24 19:29:43.264680','READY',60.50,2),(5,'2026-09-24 20:45:17.125291','READY',45.00,2),(6,'2026-09-30 18:35:40.027324','IN_PROGRESS',97.00,2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=583 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (563,'Mozzarella, Pizza sousu, Reyhan','/images/margarita.jpg','Margarita Pizza',23.00,150),(564,'Kolbasa, Mozzarella, Yaşıl reyhan, Pizza sousu','/images/pepperoni.jpg','Pepperoni Pizza',24.50,150),(565,'Hisə verilmiş dana əti, Suda Mozarella, Parmezan, Rukola, Pomidor sousu, Pizza sousu','/images/beef.jpg','Beef Pizza',26.00,150),(566,'Mozarella, Rokfor, Parmezan, Hisə verilmiş pendir, Pendir sousu','/images/4cheese.jpg','4 Cheese Pizza',26.00,150),(567,'0.33l','/images/cola.jpg','Coca-Cola / Zero',5.00,151),(568,'0.33l','/images/fanta.jpg','Fanta',5.00,151),(569,'0.33l','/images/sprite.jpg','Sprite',5.00,151),(570,'0.25l','/images/redbull.jpg','Redbull',9.00,151),(571,NULL,'/images/Profiterol.jpg','Profiterol',17.00,152),(572,NULL,'/images/Cheesecake.jpg','San Sebastian Cheesecake',15.00,152),(573,NULL,'/images/honeycake.jpg','Honey Cake',14.00,152),(574,NULL,'/images/Lemon Cheesecake.jpg','Lemon Cheesecake',15.00,152),(575,NULL,'/images/garachay.jpg','Qara Çay',14.00,153),(576,NULL,'/images/yashilchay.jpg','Yaşıl Çay',15.00,153),(577,NULL,'/images/yasemen.jpg','Yasəmən',15.00,153),(578,NULL,'/images/zencefilli.jpg','Zəncəfilli Portağal Çayı',16.00,153),(579,'Spagetti, neopolitana sous, parmezan, Dana əti, Göyərti','/images/spagetti.jpg','Spaghetti Bolognese',22.00,149),(580,'Penne, Pomidor çerri, Zeytun, Pesto sousu, Sarımsaq, Parmezan, Acılı neopolitan sousu','/images/penne.jpg','Penne All Arrabiata',23.00,149),(581,'Tagliatelli, Toyuq filesi, Göbələk, Qaymaq, Sarımsaq, Reyhan, Parmezan','/images/alfredo.jpg','Alfredo Tagliatelle',24.00,149),(582,'Spaghetti, Burrata, Qaymaq, Pesto sous, Çerri pomidor, Pramezan','/images/burrata.jpg','Spaghetti Burrata',28.00,149);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_KITCHEN','ROLE_USER') NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'$2a$10$V50NRlbonytuftbkwx4ct.X4NyqZYen955WpObFAt4jEfGcQLZQ2G','ROLE_ADMIN','admin'),(2,'$2a$10$Qq63JkSZDGFVxwk.C56bZueZbEu45ecZvCg1C8SK9dMR8oGh9Z6FG','ROLE_USER','user'),(3,'$2a$10$0ILmyo7Ng0bfD/EdpK2nTuCU1TagxmiKETLEc/yL6w32nPkHUBwFe','ROLE_KITCHEN','kitchen');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-30 22:44:54
