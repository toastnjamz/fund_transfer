-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: localhost    Database: fund_transfer
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_account_type_id` int DEFAULT NULL,
  `fk_account_user_id` int DEFAULT NULL,
  `fk_account_currency_id` int DEFAULT NULL,
  `balance` decimal(13,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id_idx` (`fk_account_user_id`),
  KEY `fk_account_currency_id_idx` (`fk_account_currency_id`),
  KEY `fk_account_type_id_idx` (`fk_account_type_id`),
  CONSTRAINT `fk_account_currency_id` FOREIGN KEY (`fk_account_currency_id`) REFERENCES `currency` (`id`),
  CONSTRAINT `fk_account_type_id` FOREIGN KEY (`fk_account_type_id`) REFERENCES `account_type` (`id`),
  CONSTRAINT `fk_account_user_id` FOREIGN KEY (`fk_account_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,1,1,1,0.00),(2,1,2,1,79.90),(3,1,3,1,100.00);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_type`
--

DROP TABLE IF EXISTS `account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_type` (
  `id` int NOT NULL,
  `account_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_type`
--

LOCK TABLES `account_type` WRITE;
/*!40000 ALTER TABLE `account_type` DISABLE KEYS */;
INSERT INTO `account_type` VALUES (1,'Regular');
/*!40000 ALTER TABLE `account_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_account_id` int DEFAULT NULL,
  `bank_account_no` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_account_id_idx` (`fk_account_id`),
  CONSTRAINT `fk_account_id` FOREIGN KEY (`fk_account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_account`
--

LOCK TABLES `bank_account` WRITE;
/*!40000 ALTER TABLE `bank_account` DISABLE KEYS */;
INSERT INTO `bank_account` VALUES (1,1,'testbankaccountno1'),(2,2,'testbankaccountno2'),(3,3,'testbankaccountno3');
/*!40000 ALTER TABLE `bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `connection`
--

DROP TABLE IF EXISTS `connection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `connection` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_user_id` int DEFAULT NULL,
  `connected_user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id_idx` (`fk_user_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`fk_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `connection`
--

LOCK TABLES `connection` WRITE;
/*!40000 ALTER TABLE `connection` DISABLE KEYS */;
INSERT INTO `connection` VALUES (1,1,2),(2,1,3),(3,2,3);
/*!40000 ALTER TABLE `connection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currency` (
  `id` int NOT NULL,
  `currency_label` varchar(6) NOT NULL,
  `currency_description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency`
--

LOCK TABLES `currency` WRITE;
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
INSERT INTO `currency` VALUES (1,'USD','US Dollars');
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_type`
--

DROP TABLE IF EXISTS `role_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_type` (
  `id` int NOT NULL,
  `role_type` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_type`
--

LOCK TABLES `role_type` WRITE;
/*!40000 ALTER TABLE `role_type` DISABLE KEYS */;
INSERT INTO `role_type` VALUES (1,'Admin'),(2,'Regular');
/*!40000 ALTER TABLE `role_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_transaction_type_id` int DEFAULT NULL,
  `fk_from_account_id` int DEFAULT NULL,
  `to_account_id` int DEFAULT NULL,
  `fk_transaction_currency_id` int DEFAULT NULL,
  `fk_bank_account_id` int DEFAULT NULL,
  `created_on` timestamp NOT NULL,
  `amount` decimal(13,2) NOT NULL,
  `description` varchar(70) DEFAULT NULL,
  `transaction_fee` decimal(13,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transaction_type_id_idx` (`fk_transaction_type_id`),
  KEY `fk_from_account_id_idx` (`fk_from_account_id`),
  KEY `fk_transaction_currency_id_idx` (`fk_transaction_currency_id`),
  KEY `fk_bank_account_id_idx` (`fk_bank_account_id`),
  CONSTRAINT `fk_bank_account_id` FOREIGN KEY (`fk_bank_account_id`) REFERENCES `bank_account` (`id`),
  CONSTRAINT `fk_from_account_id` FOREIGN KEY (`fk_from_account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `fk_transaction_currency_id` FOREIGN KEY (`fk_transaction_currency_id`) REFERENCES `currency` (`id`),
  CONSTRAINT `fk_transaction_type_id` FOREIGN KEY (`fk_transaction_type_id`) REFERENCES `transaction_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,2,1,0,1,1,'2020-06-25 08:30:20',100.00,'Adding funds from bank.',0.00),(2,1,1,2,1,1,'2020-06-25 15:30:20',10.00,'Coffee Break',0.05),(3,1,1,3,1,1,'2020-06-25 20:00:00',50.00,'Overpriced pizza, wow!',0.25),(4,3,1,0,1,1,'2020-06-25 20:15:00',39.60,'Transferring funds back to bank.',0.00),(5,2,2,0,1,2,'2020-06-25 08:30:20',100.00,'Adding funds from bank.',0.00),(6,1,2,3,1,2,'2020-06-25 15:30:20',20.00,'Office supplies',0.10),(7,2,3,0,1,3,'2020-06-24 10:35:13',100.00,'Adding funds from bank.',0.00);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_type`
--

DROP TABLE IF EXISTS `transaction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_type` (
  `id` int NOT NULL,
  `transaction_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_type`
--

LOCK TABLES `transaction_type` WRITE;
/*!40000 ALTER TABLE `transaction_type` DISABLE KEYS */;
INSERT INTO `transaction_type` VALUES (1,'Regular'),(2,'AddMoney'),(3,'TransferToBank');
/*!40000 ALTER TABLE `transaction_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fk_user_role_type_id` int DEFAULT NULL,
  `email` varchar(70) NOT NULL,
  `password` varchar(60) NOT NULL,
  `display_name` varchar(50) DEFAULT NULL,
  `created_on` datetime NOT NULL,
  `updated_on` datetime DEFAULT NULL,
  `is_active` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_user_role_type_id_idx` (`fk_user_role_type_id`),
  CONSTRAINT `fk_user_role_type_id` FOREIGN KEY (`fk_user_role_type_id`) REFERENCES `role_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,2,'user1@test.com','$2a$10$wHBd8j7yUESVRjFhZdhav.B/8sJ3TLSmmCvKrauxA8yk3ideSfB22','user1','2020-06-05 15:52:59',NULL,1),(2,2,'user2@test.com','$2a$10$wHBd8j7yUESVRjFhZdhav.B/8sJ3TLSmmCvKrauxA8yk3ideSfB22','user2','2020-06-05 15:52:59',NULL,1),(3,2,'user3@test.com','$2a$10$wHBd8j7yUESVRjFhZdhav.B/8sJ3TLSmmCvKrauxA8yk3ideSfB22','user3','2020-06-05 15:52:59',NULL,1),(4,1,'admin@test.com','$2a$10$wHBd8j7yUESVRjFhZdhav.B/8sJ3TLSmmCvKrauxA8yk3ideSfB22','admin','2020-06-05 15:52:59',NULL,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-25 22:23:27
