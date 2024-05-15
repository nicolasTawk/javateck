-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: pharmacy2_db
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `batches`
--

DROP TABLE IF EXISTS `batches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batches` (
  `batch_id` int NOT NULL AUTO_INCREMENT,
  `experation_date` datetime(6) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`batch_id`),
  KEY `FKa7qe5la0xbk0t74ysfqxwk3o0` (`product_id`),
  CONSTRAINT `FKa7qe5la0xbk0t74ysfqxwk3o0` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batches`
--

LOCK TABLES `batches` WRITE;
/*!40000 ALTER TABLE `batches` DISABLE KEYS */;
INSERT INTO `batches` VALUES (1,'2024-12-31 00:00:00.000000',50,1),(2,'2025-06-30 00:00:00.000000',75,2),(3,'2024-09-15 00:00:00.000000',100,3);
/*!40000 ALTER TABLE `batches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity_in_stock` int DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Aspirin 500mg','Aspirin',5.99,100),(2,'Ibuprofen 200mg','Ibuprofen',7.49,150),(3,'Paracetamol 500mg','Paracetamol',4.99,200);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_experation`
--

DROP TABLE IF EXISTS `product_experation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_experation` (
  `expiration_id` int NOT NULL AUTO_INCREMENT,
  `experation_date` datetime(6) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `productid` int DEFAULT NULL,
  PRIMARY KEY (`expiration_id`),
  KEY `FK58d03m9mymn3pknds0kwyvbdm` (`product_id`),
  KEY `FKb4ilskhwdi8ddqqravvcdt1xj` (`productid`),
  CONSTRAINT `FK58d03m9mymn3pknds0kwyvbdm` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKb4ilskhwdi8ddqqravvcdt1xj` FOREIGN KEY (`productid`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_experation`
--

LOCK TABLES `product_experation` WRITE;
/*!40000 ALTER TABLE `product_experation` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_experation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_location`
--

DROP TABLE IF EXISTS `product_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_location` (
  `product_location_id` int NOT NULL AUTO_INCREMENT,
  `aile` varchar(255) DEFAULT NULL,
  `bin` varchar(255) DEFAULT NULL,
  `shelf` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_location`
--

LOCK TABLES `product_location` WRITE;
/*!40000 ALTER TABLE `product_location` DISABLE KEYS */;
INSERT INTO `product_location` VALUES (1,'A1',NULL,'Shelf 1'),(2,'A2',NULL,'Shelf 2'),(3,'B1',NULL,'Shelf 3');
/*!40000 ALTER TABLE `product_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_location_mapping`
--

DROP TABLE IF EXISTS `product_location_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_location_mapping` (
  `mapping_id` int NOT NULL AUTO_INCREMENT,
  `productid` int DEFAULT NULL,
  `product_location_id` int DEFAULT NULL,
  PRIMARY KEY (`mapping_id`),
  KEY `FKs7aochol1xt9eu3f4lpr1ssji` (`productid`),
  KEY `FKplkk5huyx13900dn2x8nca13a` (`product_location_id`),
  CONSTRAINT `FKplkk5huyx13900dn2x8nca13a` FOREIGN KEY (`product_location_id`) REFERENCES `product_location` (`product_location_id`),
  CONSTRAINT `FKs7aochol1xt9eu3f4lpr1ssji` FOREIGN KEY (`productid`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_location_mapping`
--

LOCK TABLES `product_location_mapping` WRITE;
/*!40000 ALTER TABLE `product_location_mapping` DISABLE KEYS */;
INSERT INTO `product_location_mapping` VALUES (1,1,1),(2,2,2),(3,3,3);
/*!40000 ALTER TABLE `product_location_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'password123','nicolas'),(2,'password456','user2'),(3,'password789','user3');
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

-- Dump completed on 2024-05-15 20:20:47
