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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `IdOrder` bigint NOT NULL AUTO_INCREMENT,
  `IdUserCustomer` bigint NOT NULL,
  `Title` varchar(100) NOT NULL,
  `JobDescription` text NOT NULL,
  `Address` varchar(100) NOT NULL,
  `CompletionDate` date NOT NULL,
  `IdSpecialization` int NOT NULL,
  `OrderStatus` enum('under_consideration','open','close','in_work') NOT NULL,
  `CreationDate` date NOT NULL,
  PRIMARY KEY (`IdOrder`,`IdUserCustomer`),
  KEY `fk_Orders_Specializations1_idx` (`IdSpecialization`),
  KEY `fk_orders_users1_idx` (`IdUserCustomer`),
  CONSTRAINT `fk_Orders_Specializations1` FOREIGN KEY (`IdSpecialization`) REFERENCES `specializations` (`IdSpecialization`),
  CONSTRAINT `fk_orders_users1` FOREIGN KEY (`IdUserCustomer`) REFERENCES `users` (`IdUser`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (14,41,'Laying tiles1','Apartament renovation','Minsk, Vostochnaya st.','2021-09-15',6,'open','2021-08-14'),(15,44,'Laying tiles1','Apartament renovation1','Minsk, Vostochnaya st.','2021-08-15',2,'open','2021-08-11'),(17,41,'Laying tiles','Apartament renovation5','Minsk, Vostochnaya st.','2021-08-15',2,'close','2021-08-14'),(20,41,'Укладка плитки','Укладка плитки','Минск','2021-07-24',6,'close','2021-07-15'),(21,38,'Штукатурка','Штукатука в комнате 30 м2','Минск, ул. Пушкина','2021-12-09',3,'open','2021-08-23'),(22,38,'Сан-узел в квартире СРОЧНО','Выполнить разводку сан-техники в жилой квартире ','Минск, Берута 3-Б','2021-09-15',2,'open','2021-07-26'),(23,38,'Устройство фасада','Устройство фасада','д. Тарасова Минский район','2021-09-02',14,'close','2021-08-11'),(24,38,'Устройство забора','Устройство деревянного забора','Минская область, Аксаковщина','2021-09-12',15,'in_work','2021-08-03'),(25,38,'Покраска стен и потолков','Покраска с паутинкой потолков и улучшенная окраска стен','Минск, Берута 3-Б','2021-09-05',5,'open','2021-08-15'),(26,38,'Оклейка обоев','Оклейка 30 квадратов','Минск, ул. Л. Беды 5-119','2021-09-12',6,'open','2021-08-15'),(27,38,'Штукатурка','100 кв','Минск, ул. Л. Беды 5-119','2021-08-20',3,'close','2021-08-17'),(28,38,'Укладка паркетной доски','Демонтаж, укладка 100 кв','Старые Дороги, ул. Армейская д.2, кв.16','2021-08-29',8,'open','2021-08-17'),(29,41,'Устройство подвесного потолка','Устройство подвесного потолка в офисном центре 100 м2','Минск пр. Пушкина 3','2021-09-05',9,'open','2021-08-23'),(30,41,'Устройство натяжного потолка','Устройство натяжного потолка в офисном здании 300 м2','Минск ул. Орловская 10','2021-09-10',9,'open','2021-08-23');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
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
