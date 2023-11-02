CREATE DATABASE  IF NOT EXISTS `reservas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `reservas`;
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
-- Table structure for table `aerolineas`
--

DROP TABLE IF EXISTS `aerolineas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aerolineas` (
  `idAerolinea` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idAerolinea`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aerolineas`
--

LOCK TABLES `aerolineas` WRITE;
/*!40000 ALTER TABLE `aerolineas` DISABLE KEYS */;
INSERT INTO `aerolineas` VALUES (1,'Avianca'),(2,'Easyfly'),(3,'Satena'),(4,'Wingo'),(5,'Ultra Air'),(6,'Viva Air'),(7,'GCA Airlines');
/*!40000 ALTER TABLE `aerolineas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pasajeros`
--

DROP TABLE IF EXISTS `pasajeros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pasajeros` (
  `idPasajero` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  `ciudad` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idPasajero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pasajeros`
--

LOCK TABLES `pasajeros` WRITE;
/*!40000 ALTER TABLE `pasajeros` DISABLE KEYS */;
/*!40000 ALTER TABLE `pasajeros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservaciones`
--

DROP TABLE IF EXISTS `reservaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservaciones` (
  `idReservacion` bigint NOT NULL AUTO_INCREMENT,
  `idVuelo` bigint DEFAULT NULL,
  `fechaReservacion` datetime DEFAULT NULL,
  `numeroAsientos` int DEFAULT NULL,
  `idPasajero` bigint DEFAULT NULL,
  PRIMARY KEY (`idReservacion`),
  KEY `idPasajero` (`idPasajero`),
  KEY `idVuelo` (`idVuelo`),
  CONSTRAINT `reservaciones_ibfk_1` FOREIGN KEY (`idPasajero`) REFERENCES `pasajeros` (`idPasajero`),
  CONSTRAINT `reservaciones_ibfk_2` FOREIGN KEY (`idVuelo`) REFERENCES `vuelos` (`idVuelo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservaciones`
--

LOCK TABLES `reservaciones` WRITE;
/*!40000 ALTER TABLE `reservaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_vuelos`
--

DROP TABLE IF EXISTS `tipo_vuelos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_vuelos` (
  `idTipoVuelo` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idTipoVuelo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_vuelos`
--

LOCK TABLES `tipo_vuelos` WRITE;
/*!40000 ALTER TABLE `tipo_vuelos` DISABLE KEYS */;
INSERT INTO `tipo_vuelos` VALUES (1,'publico'),(2,'privado');
/*!40000 ALTER TABLE `tipo_vuelos` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Dumping routines for database 'reservas'
--
/*!50003 DROP FUNCTION IF EXISTS `generarCodigo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `generarCodigo`(idAero int ) RETURNS varchar(10) CHARSET utf8mb4
    DETERMINISTIC
BEGIN
	declare contador_id int;
    declare codigo_aerolinea varchar(10);
	set contador_id = (select count(vuelos.idAerolinea) from vuelos where idAerolinea = idAero);
    IF(idAero = 1) THEN
		set codigo_aerolinea = concat('AV',LPAD(contador_id + 1, 4, '0'));
	END IF;
    IF(idAero = 2) THEN
		set codigo_aerolinea = concat('EA',LPAD(contador_id + 1, 4, '0'));
	END IF;
    IF(idAero = 3) THEN
		set codigo_aerolinea = concat('SA',LPAD(contador_id + 1, 4, '0'));
	END IF;
    IF(idAero = 4) THEN
		set codigo_aerolinea = concat('WI',LPAD(contador_id + 1, 4, '0'));
	END IF;
    IF(idAero = 5) THEN
		set codigo_aerolinea = concat('UL',LPAD(contador_id + 1, 4, '0'));
	END IF;
    IF(idAero = 6) THEN
		set codigo_aerolinea = concat('VI',LPAD(contador_id + 1, 4, '0'));
	END IF;
    IF(idAero = 7) THEN
		set codigo_aerolinea = concat('BI',LPAD(contador_id + 1, 4, '0'));
	END IF;
RETURN codigo_aerolinea;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_vuelo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_vuelo`(origen_ varchar(255), destino_ varchar(255), fechaPartida_ datetime, fechaLlegada_ datetime, precio_ decimal(10,2), asientos_ long, idTipoVuelo_ int, idAerolinea_ int)
BEGIN
	insert into vuelos(codigoVuelo, origen, destino, fechaPartida, fechaLlegada, precio, asientos, idTipoVuelo, idAerolinea)
	values((select generarCodigo(idAerolinea_)),origen_, destino_, fechaPartida_, fechaLlegada_, precio_, asientos_, idTipoVuelo_, idAerolinea_);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_vuelo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_vuelo`(idVuelo_ long, origen_ varchar(255), destino_ varchar(255), fechaPartida_ datetime, fechaLlegada_ datetime, precio_ decimal(10,2), asientos_ long, idTipoVuelo_ int, idAerolinea_ int)
BEGIN
	update vuelos set codigoVuelo = (select generarCodigo(idAerolinea_)), origen = origen_, destino = destino_, fechaPartida = fechaPartida_, fechaLlegada = fechaLlegada_, precio = precio_, asientos = asientos_, idTipoVuelo = idTipoVuelo_, idAerolinea = idAerolinea_ where idVuelo = idVuelo_;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-02 11:46:34
