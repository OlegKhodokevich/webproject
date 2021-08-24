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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `IdUser` bigint NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `EncodedPassword` varchar(64) NOT NULL,
  `EMail` varchar(45) NOT NULL,
  `Phone` varchar(13) NOT NULL,
  `IdRegion` int NOT NULL,
  `City` varchar(60) NOT NULL,
  `UserStatus` enum('declared','confirmed','archived') NOT NULL,
  `UserRole` enum('guess','user','admin','customer','executor') NOT NULL,
  PRIMARY KEY (`IdUser`),
  UNIQUE KEY `Phone_UNIQUE` (`Phone`),
  UNIQUE KEY `EMail_UNIQUE` (`EMail`),
  KEY `fk_Users_Regions1_idx` (`IdRegion`),
  CONSTRAINT `fk_Users_Regions1` FOREIGN KEY (`IdRegion`) REFERENCES `regions` (`IdRegion`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (33,'Семён','Семёнович','$2a$10$Vaz/Zeq2zB/TGZx1pqGfK./xTiaB22gGsquRphE1Fz9GPCg8wgfJO','ExecutorUser@gmail.com','+375293112211',1,'d','confirmed','executor'),(34,'Иван','Васильевич','$2a$10$jvCkZIXvN.Cv6DBK35qyIOsKUsP68Itszk5AQJPAzjgPwCBRmRB0q','ExecutorUser2@gmail.com','+375297889955',1,'Минск, ул. Колесникова ','confirmed','executor'),(38,'Заказчик','Пользователь','$2a$10$okCEF0jVMscUYU8V2.TiD.pEudxokBC87v0oAqeW64Aa3AiwHxTLm','CustomerUserWeb@gmail.com','+375293668866',4,'Орша','confirmed','customer'),(41,'Пётр','Петров','$2a$10$CqbrFxVsNg8M6Bebcy52FuoC2hKAVbS8tOaLdr.wdqV7xcZMlVgla','CustomerUserweb1@gmail.com','+375291778899',2,'Гомель','confirmed','customer'),(44,'Иван','Иванович','$2a$10$okCEF0jVMscUYU8V2.TiD.pEudxokBC87v0oAqeW64Aa3AiwHxTLm','Test@test.by','+375291228877',1,'Минск','confirmed','customer'),(45,'Patric','Ice','$2a$10$EykJETST4H2QwvnSvz8Y4uDFYT5VYX2dAS4sq0SusmfKKVHaFMG7W','ExecutorUser3@gmail.com','+375293372548',3,'Mogilev','confirmed','executor'),(46,'Maks','Force','$2a$10$DqM1aLTYI2zB0wi/PWuCfu0yjApAXIyNADSXesC0ANKaCfchOHIoe','ExecutorUser4@gmail.com','+375293372549',6,'Brest','confirmed','executor'),(49,'Oleg','Khodokevich','$2a$10$pXBoJSXlwWCyuq3b/tVaRuFRYExLWKHZncq37GDqa0WxYDjzFkyhG','yourbestbuilderone@gmail.com','+375293372547',1,'Минск','confirmed','admin'),(55,'Иван','Фёдорович','$2a$10$NL5f.UV6v.L2i3.JsrKVceLCKtZthl3U1/3EzqawL5vlLOdpeOrjK','executoruser5@gmail.com','+375293372543',4,'Витебск','confirmed','customer'),(56,'Фёдор','Иванович','$2a$10$UwkuSXXSxdN86GOMkz7VSea.OyzjQMcfs9IsaUMo94Z/QZ1ZkZaPa','customeruserweb2@gmail.com','+375293372645',1,'Минск','confirmed','customer');
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

-- Dump completed on 2021-08-24 19:38:16
