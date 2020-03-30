-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: dp2_spring
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
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (100,0,'admin1@gmail.com','https://estaticos.muyinteresante.es/media/cache/400x300_thumb/uploads/images/dossier/5e6b9e535cafe8decee46032/fe-lix-rodri-guez-de-la-fuente.jpg','Name cero','Surname cero','+34 694567234',32),(101,0,'admin2@gmail.com','','Name uno','Surname uno','+34 694567235',33),(102,0,'admin3@gmail.com','','Name dos','Surname dos','+34 694567236',34);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `certificate`
--

LOCK TABLES `certificate` WRITE;
/*!40000 ALTER TABLE `certificate` DISABLE KEYS */;
INSERT INTO `certificate` VALUES (50,0,'Certificado para curso de principiantes','Entity1'),(51,0,'Certificado para curso de animales exoticos','Entity2'),(52,0,'Certificado para curso de nivel experto','Entity3'),(53,0,'Certificado para curso de nivel intermedio','Entity4'),(54,0,'Certificado para curso de roedores','Entity5'),(55,0,'Certificado para curso de viboras','Entity6');
/*!40000 ALTER TABLE `certificate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `clerk`
--

LOCK TABLES `clerk` WRITE;
/*!40000 ALTER TABLE `clerk` DISABLE KEYS */;
INSERT INTO `clerk` VALUES (200,0,'clerk1@gmail.com','https://estaticos.muyinteresante.es/media/cache/400x300_thumb/uploads/images/dossier/5e6b9e535cafe8decee46032/fe-lix-rodri-guez-de-la-fuente.jpg','Name cero','Surname cero','+34 694567234',35),(201,0,'clerk2@gmail.com','','Name uno','Surname uno','+34 694567235',36),(202,0,'clerk3@gmail.com','','Name dos','Surname dos','+34 694567236',37);
/*!40000 ALTER TABLE `clerk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (400,0,'Curso de principantes','2019-12-20 12:12:12.000000',10.99,'2019-06-20 12:12:12.000000',50,200),(401,0,'Curso de animales exoticos','2019-12-20 12:12:12.000000',10.99,'2019-06-20 12:12:12.000000',51,201),(402,0,'Curso nivel experto','2020-12-20 12:12:12.000000',10.99,'2020-06-20 12:12:12.000000',52,202),(403,0,'Curso nivel intermedio','2020-12-20 12:12:12.000000',10.99,'2020-06-20 12:12:12.000000',53,202),(404,0,'Curso para roedores','2020-12-20 12:12:12.000000',10.99,'2020-06-20 12:12:12.000000',54,201),(405,0,'Curso para viboras','2020-12-20 12:12:12.000000',10.99,'2020-06-20 12:12:12.000000',55,201);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `course_owners_registered`
--

LOCK TABLES `course_owners_registered` WRITE;
/*!40000 ALTER TABLE `course_owners_registered` DISABLE KEYS */;
INSERT INTO `course_owners_registered` VALUES (400,60),(400,61),(401,62); 
/*!40000 ALTER TABLE `course_owners_registered` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `insurance`
--

LOCK TABLES `insurance` WRITE;
/*!40000 ALTER TABLE `insurance` DISABLE KEYS */;
INSERT INTO `insurance` VALUES (20,0,'Ley 1'),(21,0,'Ley 2'),(22,0,'Ley 3'),(23,0,'Ley 4'),(24,0,'Ley 5'),(25,0,'Ley 6');
/*!40000 ALTER TABLE `insurance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES (60,0,'owner1@gmail.com','https://estaticos.muyinteresante.es/media/cache/400x300_thumb/uploads/images/dossier/5e6b9e535cafe8decee46032/fe-lix-rodri-guez-de-la-fuente.jpg','Name cero','Surname cero','+34 694567234',38),(61,0,'owner2@gmail.com','','Name uno','Surname uno','+34 694567235',39),(62,0,'owner3@gmail.com','','Name dos','Surname dos','+34 694567236',40);
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `pet`
--

LOCK TABLES `pet` WRITE;
/*!40000 ALTER TABLE `pet` DISABLE KEYS */;
INSERT INTO `pet` VALUES (80,0,'2020-06-20 12:12:12.000000','labrador','DOMESTIC',20,60),
(81,0,'2020-06-20 12:12:12.000000','Cobaya','WILD',21,60),
(82,0,'2020-06-20 12:12:12.000000','serpiente','EXOTIC',22,61),
(83,0,'2020-06-20 12:12:12.000000','yorkshire','DOMESTIC',23,62),
(84,0,'2020-06-20 12:12:12.000000','raton','DOMESTIC',24,60),
(85,0,'2020-06-20 12:12:12.000000','gato','DOMESTIC',25,62);
/*!40000 ALTER TABLE `pet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transport`
--

LOCK TABLES `transport` WRITE;
/*!40000 ALTER TABLE `transport` DISABLE KEYS */;
INSERT INTO `transport` VALUES (500,0,null,'Madrid','Rota','PENDING',null),(501,0,'SEUR','Sevilla','Lebrija','TRANSPORTED',200),(502,0,null,'Utrera','Sanchez Perrier 6','PENDING',null),(503,0,null,'Plaza españa','Wizink center','PENDING',null),(504,0,'SEUR','Rotonda beca','Puente del dragón','TRANSPORTED',202),(505,0,null,'Mairena','Carmona','PENDING',null);
/*!40000 ALTER TABLE `transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transport_pets`
--

LOCK TABLES `transport_pets` WRITE;
/*!40000 ALTER TABLE `transport_pets` DISABLE KEYS */;
INSERT INTO `transport_pets` VALUES (500,80),(500,81),(501,82),(502,83),(503,84),(504,84),(505,84);
/*!40000 ALTER TABLE `transport_pets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (32,0,_binary '\0','$2a$05$liN1q4inTShcELQ373gIeOyeVDl1mWqAMUEEDmC.jnMw1il7TWp0S','admin1'),(33,0,_binary '\0','$2a$05$6w7s9gZZvfqw9ISAYMerM.I6zuZ55xWdKFv.ImSf7pbxs9PWUDt2q','admin2'),(34,0,_binary '\0','$2a$05$TXrmZFnAZ2l68enU7HfNM.aaPC4lZ8zHqwkvht5bwaqeGxloJFoo2','admin3'),(35,0,_binary '\0','$2y$12$2VG3.3TDeFqcDjvjlawWc.xYYyFJi5dfzDhO9TD9DB2nXzwCGMO06','clerk1'),(36,0,_binary '\0','$2y$12$Cqy/j0xxBpOmvoQLWfsrSuIWEOdBnnCRxiVmeDScb4L0rZGn42it2','clerk2'),(37,0,_binary '\0','$2y$12$2Mr27QEcx4a5cSoQO8WSh.kl/BywNLj0fbST/s1s7PITtaeL4yt8a','clerk3'),(38,0,_binary '\0','$2y$12$NJGlG0LmI3.qZ1/9a2vJ7u9cHo91AI4AdWaFBbQxyJDui41JuXPzO','owner1'),(39,0,_binary '\0','$2y$12$D5FnwFlM2Ie4htCViEANK.ywJmFiDAE5GBzFNfBp/dJkfO7utB8.m','owner2'),(40,0,_binary '\0','$2y$12$W3yGNuLHEU0tAX0Eclq28uQYtsXFOWDV89R6NyDUwyPWToc86VxHG','owner3');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (32,'ADMIN'),(33,'ADMIN'),(34,'ADMIN'),(35,'CLERK'),(36,'CLERK'),(37,'CLERK'),(38,'OWNER'),(39,'OWNER'),(40,'OWNER');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-20 19:45:17
