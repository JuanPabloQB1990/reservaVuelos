-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: reservas
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `vuelos`
--

DROP TABLE IF EXISTS `vuelos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vuelos` (
  `idVuelo` bigint NOT NULL AUTO_INCREMENT,
  `codigoVuelo` varchar(255) DEFAULT NULL,
  `origen` varchar(255) DEFAULT NULL,
  `destino` varchar(255) DEFAULT NULL,
  `fechaPartida` datetime(6) NOT NULL,
  `fechaLlegada` datetime(6) NOT NULL,
  `precio` double NOT NULL,
  `asientos` bigint NOT NULL,
  `idTipoVuelo` bigint DEFAULT NULL,
  `idAerolinea` bigint DEFAULT NULL,
  PRIMARY KEY (`idVuelo`),
  UNIQUE KEY `idVuelo` (`idVuelo`),
  KEY `idTipoVuelo` (`idTipoVuelo`),
  KEY `idAerolinea` (`idAerolinea`),
  CONSTRAINT `vuelos_ibfk_1` FOREIGN KEY (`idTipoVuelo`) REFERENCES `tipo_vuelos` (`idTipoVuelo`),
  CONSTRAINT `vuelos_ibfk_2` FOREIGN KEY (`idAerolinea`) REFERENCES `aerolineas` (`idAerolinea`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vuelos`
--

LOCK TABLES `vuelos` WRITE;
/*!40000 ALTER TABLE `vuelos` DISABLE KEYS */;
INSERT INTO `vuelos` VALUES (5,'EA0001','Bogota','Nueva York','2023-11-01 12:55:02.000000','2023-11-01 18:55:02.000000',21600,5,1,2),(6,'AV0001','Medellin','Nueva York','2023-11-01 12:55:02.000000','2023-11-01 17:55:02.000000',22600,5,1,1),(7,'SA0001','Cali','Nueva York','2023-11-01 12:55:02.000000','2023-11-01 19:55:02.000000',22800,5,1,3),(8,'SA0002','Cali','Medellin','2023-11-02 12:55:02.000000','2023-11-02 15:55:02.000000',2500,5,1,3),(9,'SA0003','Bogota','Medellin','2023-11-02 12:55:02.000000','2023-11-02 15:55:02.000000',2500,5,1,3),(10,'SA0004','Bogota','Medellin','2023-11-02 14:55:02.000000','2023-11-02 15:55:02.000000',2500,5,1,3),(11,'SA0005','Bogota','Medellin','2023-11-02 15:55:02.000000','2023-11-02 16:55:02.000000',2500,5,1,3);
/*!40000 ALTER TABLE `vuelos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-02 10:58:44
