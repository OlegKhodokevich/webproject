-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `contracts`
--

DROP TABLE IF EXISTS `contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contracts` (
  `IdContract` bigint NOT NULL AUTO_INCREMENT,
  `IdOrder` bigint NOT NULL,
  `IdUserExecutor` bigint NOT NULL,
  `Conclude` enum('under_consideration','concluded','not_concluded') NOT NULL,
  `Complete` enum('completed','not_completed') NOT NULL,
  PRIMARY KEY (`IdOrder`,`IdUserExecutor`),
  UNIQUE KEY `IdContract_UNIQUE` (`IdContract`),
  KEY `fk_contracts_executors1_idx` (`IdUserExecutor`),
  KEY `fk_contracts_orders1_idx` (`IdOrder`),
  CONSTRAINT `fk_contracts_executors1` FOREIGN KEY (`IdUserExecutor`) REFERENCES `executors` (`IdUserExecutor`),
  CONSTRAINT `fk_contracts_orders1` FOREIGN KEY (`IdOrder`) REFERENCES `orders` (`IdOrder`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracts`
--

LOCK TABLES `contracts` WRITE;
/*!40000 ALTER TABLE `contracts` DISABLE KEYS */;
INSERT INTO `contracts` VALUES (35,15,34,'under_consideration','not_completed'),(38,17,34,'under_consideration','not_completed'),(31,20,34,'under_consideration','not_completed'),(32,20,45,'under_consideration','not_completed'),(33,20,46,'under_consideration','not_completed'),(27,21,33,'not_concluded','not_completed'),(28,21,34,'under_consideration','not_completed'),(29,21,45,'under_consideration','not_completed'),(30,21,46,'under_consideration','not_completed'),(22,22,34,'under_consideration','not_completed'),(21,22,45,'under_consideration','not_completed'),(20,22,46,'under_consideration','not_completed'),(23,23,33,'not_concluded','not_completed'),(24,23,34,'concluded','completed'),(25,23,45,'not_concluded','not_completed'),(26,23,46,'not_concluded','not_completed'),(18,24,33,'not_concluded','not_completed'),(15,24,34,'not_concluded','not_completed'),(19,24,46,'concluded','not_completed'),(39,25,34,'under_consideration','not_completed'),(37,26,34,'under_consideration','not_completed'),(40,26,45,'under_consideration','not_completed'),(41,30,34,'under_consideration','not_completed');
/*!40000 ALTER TABLE `contracts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-24 19:38:16
