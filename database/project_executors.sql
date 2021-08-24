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
-- Table structure for table `executors`
--

DROP TABLE IF EXISTS `executors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `executors` (
  `IdUserExecutor` bigint NOT NULL,
  `PersonalFoto` varchar(200) DEFAULT '',
  `UNP` varchar(12) DEFAULT '',
  `AverageMark` float DEFAULT '0',
  `NumberCompletionContracts` int DEFAULT '0',
  `NumberContractsInProgress` int DEFAULT '0',
  `DescriptionExecutor` varchar(200) DEFAULT '',
  PRIMARY KEY (`IdUserExecutor`),
  KEY `fk_executors_users1_idx` (`IdUserExecutor`),
  CONSTRAINT `fk_executors_users1` FOREIGN KEY (`IdUserExecutor`) REFERENCES `users` (`IdUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executors`
--

LOCK TABLES `executors` WRITE;
/*!40000 ALTER TABLE `executors` DISABLE KEYS */;
INSERT INTO `executors` VALUES (33,'../image/users_photo/User1.jpg','100008858',0,0,0,'Выполняю качественно и быстро, стоимость всех работ оговаривается заранее.'),(34,'../image/users_photo/default.png','100008958',5,1,0,'Вовремя и качественно! '),(45,'../image/users_photo/default.png','100008859',0,0,0,'The best of the best. Work experience is 10 years.'),(46,'../image/users_photo/default.png','100008859',0,0,0,'I love my job. Work experience is 7 years.');
/*!40000 ALTER TABLE `executors` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-24 19:38:17
