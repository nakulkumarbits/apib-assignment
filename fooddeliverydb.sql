-- MySQL dump 10.13  Distrib 8.3.0, for Linux (aarch64)
--
-- Host: localhost    Database: fooddeliverydb
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Address`
--

DROP TABLE IF EXISTS `Address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addressLine1` varchar(255) DEFAULT NULL,
  `addressLine2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `pinCode` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Address`
--

LOCK TABLES `Address` WRITE;
/*!40000 ALTER TABLE `Address` DISABLE KEYS */;
INSERT INTO `Address` VALUES (1,'14/374 First floor, DDA Flats','Madangir','New Delhi','2024-10-27 17:54:48.361651','2024-10-27 17:54:48.361651','110062','Delhi',0),(4,'36/1200 DDA Flats','Madangir','New Delhi','2024-10-27 17:59:37.001539','2024-10-27 17:59:37.001539','110062','Delhi',0),(5,'36/1200 DDA Flats','Madangir','New Delhi','2024-10-27 18:00:58.231530','2024-10-27 18:00:58.231530','110062','Delhi',0),(6,'C-36 Galaxy apartments','5th Floor, Malviya nagar','New Delhi','2024-10-27 18:03:28.890532','2024-10-27 18:03:28.890532','110017','Delhi',0),(7,'B-II/407 Second floor','Lodhi road','New Delhi','2024-10-27 18:06:50.807930','2024-10-27 18:06:50.807930','110025','Delhi',0),(8,'J-10 First floor, Main market','Greater kailash-I','New Delhi','2024-10-27 18:12:55.494596','2024-10-27 18:12:55.494596','110048','Delhi',0),(9,'1456 Saket Complex','Select City Walk, Saket','New Delhi','2024-10-27 18:15:47.457810','2024-10-27 18:15:47.457810','110017','Delhi',0),(11,'M Block, 129/2 Main market','Greater Kailash-I','New Delhi','2024-10-27 18:20:08.532654','2024-10-27 18:20:08.532654','110048','Delhi',0),(12,'A-245/5, Ground Floor, Devli Rd, Pocket A','Devli Road, Khanpur','New Delhi','2024-10-27 18:25:29.287587','2024-10-27 18:25:29.287587','110044','Delhi',0),(13,'A-44, Central Market, Sector 5','Doctor Ambedkar Nagar, Madangir','New Delhi','2024-10-27 18:29:25.687876','2024-10-27 18:29:25.687876','110062','Delhi',0),(14,'56/1 First floor, Uttam nagar','Near SBI bank','Delhi','2024-10-27 18:44:52.898946','2024-10-27 18:44:52.898946','110076','Delhi',0),(15,'G-14 Block G','Savitri Nagar','New Delhi','2024-10-27 18:46:06.416488','2024-10-27 18:46:06.416488','110048','Delhi',0),(16,'G-14 Block G','Savitri Nagar','New Delhi','2024-10-27 18:46:46.483405','2024-10-27 18:46:46.483405','110048','Delhi',0),(17,'14/374 First floor, DDA Flats','Madangir, Dr Ambedkar Nagar','New Delhi','2024-10-28 19:53:24.130302','2024-10-28 19:53:24.130302','110062','Delhi',0),(18,'C-56/1 First floor, Uttam nagar','Near SBI bank','New Delhi','2024-10-28 20:04:25.194880','2024-10-28 20:04:25.194880','110076','Delhi',0),(19,'A-44, Central Market, Sector 5','Doctor Ambedkar Nagar, Madangir','New Delhi','2024-10-28 20:30:56.545053','2024-10-28 20:30:56.545053','110062','Delhi',0),(20,'A-245/5, Ground Floor, Devli Rd, Pocket A','Devli Road, Khanpur','New Delhi','2024-10-28 20:44:24.710305','2024-10-28 20:44:24.710305','110044','Delhi',0),(21,'1456 Saket Complex','Select City Walk, Saket','New Delhi','2024-10-28 23:58:11.261413','2024-10-28 23:58:11.261413','110017','Delhi',0),(22,'1456 Saket Complex','Select City Walk, Saket','New Delhi','2024-10-28 23:58:50.392731','2024-10-28 23:58:50.392731','110017','Delhi',0),(23,'1456 Saket Complex','Select City Walk, Saket','New Delhi','2024-10-28 23:59:41.262514','2024-10-28 23:59:41.262514','110017','Delhi',0);
/*!40000 ALTER TABLE `Address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Administrator`
--

DROP TABLE IF EXISTS `Administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Administrator` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcsfxiaqnaple91nkb0fa3p5s0` (`user_id`),
  CONSTRAINT `FK8hau117bg0amv9oi6hfvauptc` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Administrator`
--

LOCK TABLES `Administrator` WRITE;
/*!40000 ALTER TABLE `Administrator` DISABLE KEYS */;
INSERT INTO `Administrator` VALUES (1,1);
/*!40000 ALTER TABLE `Administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BlacklistedToken`
--

DROP TABLE IF EXISTS `BlacklistedToken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BlacklistedToken` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BlacklistedToken`
--

LOCK TABLES `BlacklistedToken` WRITE;
/*!40000 ALTER TABLE `BlacklistedToken` DISABLE KEYS */;
/*!40000 ALTER TABLE `BlacklistedToken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `mobileNo` varchar(255) NOT NULL,
  `address_id` bigint DEFAULT NULL,
  `payment_detail_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3qgg01qojcmbdp47dkaom9x45` (`email`),
  UNIQUE KEY `UKi7f12t1rqxjnufl1k69g1887v` (`address_id`),
  UNIQUE KEY `UKlkvkcfp0vrq50v228378wp8sa` (`payment_detail_id`),
  UNIQUE KEY `UK8mymjl93593fro12fetnugjxr` (`user_id`),
  CONSTRAINT `FK6fcmqdibqefuoimvjyji0a8xu` FOREIGN KEY (`payment_detail_id`) REFERENCES `PaymentDetail` (`id`),
  CONSTRAINT `FK6y0rqloalxi6r6lpoqtfohp9i` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKfok4ytcqy7lovuiilldbebpd9` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customer`
--

LOCK TABLES `Customer` WRITE;
/*!40000 ALTER TABLE `Customer` DISABLE KEYS */;
INSERT INTO `Customer` VALUES (1,'2023mt93202@wilp.bits-pilani.ac.in','Nakul','Kumar','9911212289',17,1,2),(4,'abhishekgupta@gmail.com','Abhishek','gupta','9911223344',4,4,5),(5,'divyabajaj@gmail.com','Divya','Bajaj','9911773344',5,5,6),(6,'yash.kalra@gmail.com','Yash','Kalra','8760123500',6,6,7),(7,'priyanka1011@yahoo.co.in','Priyanka','Sansanwal','9856011007',7,7,8);
/*!40000 ALTER TABLE `Customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DeliveryPersonnel`
--

DROP TABLE IF EXISTS `DeliveryPersonnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DeliveryPersonnel` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `vehicleType` enum('FOUR_WHEELER','TWO_WHEELER') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3kg10lc6v68heiwtes2aaqjkx` (`user_id`),
  UNIQUE KEY `UKk7lt86tncb8trb16ry7if05mi` (`address_id`),
  CONSTRAINT `FKe2p5fmq53r6w8o3x0srnjhiu2` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKl78iipn6rri5odq2sypst2xjq` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DeliveryPersonnel`
--

LOCK TABLES `DeliveryPersonnel` WRITE;
/*!40000 ALTER TABLE `DeliveryPersonnel` DISABLE KEYS */;
INSERT INTO `DeliveryPersonnel` VALUES (1,'TWO_WHEELER',15,18),(2,'TWO_WHEELER',16,15),(3,'TWO_WHEELER',17,16);
/*!40000 ALTER TABLE `DeliveryPersonnel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MenuItem`
--

DROP TABLE IF EXISTS `MenuItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MenuItem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdDate` datetime(6) DEFAULT NULL,
  `cuisine` enum('CHINESE','INDIAN','ITALIAN','KOREAN','MEXICAN','MUGHLAI') DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `itemAvailable` enum('NO','YES') DEFAULT NULL,
  `itemType` enum('NON_VEG','VEG') DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `version` bigint DEFAULT NULL,
  `restaurant_owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeuojx6ppdp8f387uj2thbssig` (`restaurant_owner_id`),
  CONSTRAINT `FKeuojx6ppdp8f387uj2thbssig` FOREIGN KEY (`restaurant_owner_id`) REFERENCES `Restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MenuItem`
--

LOCK TABLES `MenuItem` WRITE;
/*!40000 ALTER TABLE `MenuItem` DISABLE KEYS */;
INSERT INTO `MenuItem` VALUES (1,'2024-10-28 20:35:18.195826','CHINESE','Veg Hakka Noodles','YES','VEG','2024-10-28 20:35:18.195826','Veg Hakka Noodles',219,0,5),(2,'2024-10-28 20:37:58.028919','CHINESE','Chilly Chicken Dry','YES','NON_VEG','2024-10-28 20:37:58.028919','Chilly Chicken Dry',309,0,5),(3,'2024-10-28 20:38:46.901004','CHINESE','Chicken Noodles','YES','VEG','2024-10-28 20:39:44.437260','Chicken Noodles',199,1,5),(4,'2024-10-28 20:40:57.135124','CHINESE','Prawns Fried Rice','YES','NON_VEG','2024-10-28 20:40:57.135124','Prawns Fried Rice',409,0,5),(5,'2024-10-28 20:46:50.576667','MUGHLAI','Mutton stirred in with curd water and a melonge of spices along with garlic-ginger cloves cardamom and cinnamon sticks.','YES','NON_VEG','2024-10-28 20:46:50.576667','Mutton Nihari',560,0,4),(6,'2024-10-28 20:48:04.083372','MUGHLAI','Butter chicken with bone','YES','NON_VEG','2024-10-28 20:48:04.083372','Butter chicken with bone',350,0,4),(7,'2024-10-28 20:48:57.122543','MUGHLAI','A royal delicious assemblage of fine flavors brewed with mutton and spices.','YES','NON_VEG','2024-10-28 20:48:57.122543','Mutton Stew',560,0,4),(8,'2024-10-28 21:56:06.627361','MUGHLAI','Chicken mince with fragrant spices almonds and cashew nuts cooked in mughlai style.','YES','NON_VEG','2024-10-28 21:56:06.627361','Chicken Mughlai Keema',370,0,4),(9,'2024-10-29 00:08:46.443985','INDIAN','Silky rich, and buttery slow cooked black lentils with mellow smokiness, a Punjab Grill\'s signature.','YES','VEG','2024-10-29 00:08:46.443985','Dal Makhani',455,0,2),(10,'2024-10-29 00:10:41.017757','INDIAN','Paneer simmered in onion and bell peppers kadhai masala tempered by whole coriander.','YES','VEG','2024-10-29 00:10:41.017757','Kadhai Paneer',455,0,2),(11,'2024-10-29 00:11:34.028595','INDIAN','Soft Indian flatbread coated with butter.','YES','VEG','2024-10-29 00:11:34.028595','Butter Naan',85,0,2),(12,'2024-10-29 00:12:17.738018','INDIAN','Nutty flavoured savoury flatbreads.','YES','VEG','2024-10-29 00:12:17.738018','Missi roti',79,0,2);
/*!40000 ALTER TABLE `MenuItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderDetail`
--

DROP TABLE IF EXISTS `OrderDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderDetail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdDate` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `orderStatus` enum('ACCEPTED','AWAITING_CONFIRMATION','CANCELLED','DELIVERED','IN_PREPARATION','OUT_FOR_DELIVERY','READY_FOR_DELIVERY','REJECTED') NOT NULL,
  `paymentMethod` enum('CASH','CREDIT_CARD','DEBIT_CARD','UPI') NOT NULL,
  `totalAmount` double NOT NULL,
  `version` bigint DEFAULT NULL,
  `customer_id` bigint NOT NULL,
  `payment_id` bigint DEFAULT NULL,
  `restaurant_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnw8e5ie5lb7pw9db1xwy1sj0c` (`payment_id`),
  KEY `FKlwylj1qrsxh84g8d7er0f5585` (`customer_id`),
  KEY `FK2vnd6yad4877v3soxcqk937u1` (`restaurant_id`),
  CONSTRAINT `FK2vnd6yad4877v3soxcqk937u1` FOREIGN KEY (`restaurant_id`) REFERENCES `Restaurant` (`id`),
  CONSTRAINT `FKlwylj1qrsxh84g8d7er0f5585` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`id`),
  CONSTRAINT `FKso7egdy3vsx02j9rh8bd2tv95` FOREIGN KEY (`payment_id`) REFERENCES `OrderPaymentDetail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderDetail`
--

LOCK TABLES `OrderDetail` WRITE;
/*!40000 ALTER TABLE `OrderDetail` DISABLE KEYS */;
INSERT INTO `OrderDetail` VALUES (1,'2024-10-30 12:03:11.651994','2024-10-30 12:30:41.211814','DELIVERED','UPI',850,3,1,NULL,2),(2,'2024-10-30 12:04:50.542300','2024-10-30 12:47:00.995608','REJECTED','UPI',817,1,1,NULL,5),(4,'2024-10-30 12:06:26.974055','2024-10-30 12:06:26.974055','AWAITING_CONFIRMATION','UPI',1490,0,1,NULL,4),(5,'2024-10-31 18:18:57.442931','2024-10-31 18:18:57.442931','AWAITING_CONFIRMATION','UPI',1490,0,1,NULL,4),(6,'2024-10-31 18:19:01.202744','2024-10-31 18:19:01.202744','AWAITING_CONFIRMATION','UPI',1490,0,1,NULL,4),(7,'2024-10-31 18:19:48.553970','2024-10-31 18:19:48.553970','AWAITING_CONFIRMATION','UPI',1490,0,1,1,4),(8,'2024-10-31 18:19:50.515890','2024-10-31 18:19:50.515890','AWAITING_CONFIRMATION','UPI',1490,0,1,2,4);
/*!40000 ALTER TABLE `OrderDetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderItem`
--

DROP TABLE IF EXISTS `OrderItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderItem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `totalPrice` double NOT NULL,
  `menu_item_id` bigint NOT NULL,
  `order_detail_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaqqkf7tjdpqcuohm2g7uww24q` (`menu_item_id`),
  KEY `FK7cmys9vguhxa89fd095xyeyje` (`order_detail_id`),
  CONSTRAINT `FK7cmys9vguhxa89fd095xyeyje` FOREIGN KEY (`order_detail_id`) REFERENCES `OrderDetail` (`id`),
  CONSTRAINT `FKaqqkf7tjdpqcuohm2g7uww24q` FOREIGN KEY (`menu_item_id`) REFERENCES `MenuItem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderItem`
--

LOCK TABLES `OrderItem` WRITE;
/*!40000 ALTER TABLE `OrderItem` DISABLE KEYS */;
INSERT INTO `OrderItem` VALUES (1,79,5,395,12,1),(2,455,1,455,10,1),(3,199,1,199,3,2),(4,309,2,618,2,2),(5,370,1,370,8,4),(6,560,2,1120,5,4),(7,370,1,370,8,5),(8,560,2,1120,5,5),(9,370,1,370,8,6),(10,560,2,1120,5,6),(11,370,1,370,8,7),(12,560,2,1120,5,7),(13,370,1,370,8,8),(14,560,2,1120,5,8);
/*!40000 ALTER TABLE `OrderItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderPaymentDetail`
--

DROP TABLE IF EXISTS `OrderPaymentDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderPaymentDetail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `paymentMethod` enum('CASH','CREDIT_CARD','DEBIT_CARD','UPI') NOT NULL,
  `paymentStatus` enum('COMPLETED','IN_PROGRESS','REJECTED') NOT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderPaymentDetail`
--

LOCK TABLES `OrderPaymentDetail` WRITE;
/*!40000 ALTER TABLE `OrderPaymentDetail` DISABLE KEYS */;
INSERT INTO `OrderPaymentDetail` VALUES (1,1490,'2024-10-31 18:19:48.559257','2024-10-31 18:19:48.559257','UPI','COMPLETED',0),(2,1490,'2024-10-31 18:19:50.515986','2024-10-31 18:19:50.515986','UPI','COMPLETED',0);
/*!40000 ALTER TABLE `OrderPaymentDetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PaymentDetail`
--

DROP TABLE IF EXISTS `PaymentDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PaymentDetail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cardNumber` varchar(255) DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `paymentMethod` enum('CASH','CREDIT_CARD','DEBIT_CARD','UPI') NOT NULL,
  `upiId` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PaymentDetail`
--

LOCK TABLES `PaymentDetail` WRITE;
/*!40000 ALTER TABLE `PaymentDetail` DISABLE KEYS */;
INSERT INTO `PaymentDetail` VALUES (1,NULL,'2024-10-27 17:54:48.371613','2024-10-27 17:54:48.371613','UPI','nakulk@okicici',0),(4,NULL,'2024-10-27 17:59:37.007819','2024-10-27 17:59:37.007819','UPI','9911223344@upi',0),(5,'4752-4350-0098-1123','2024-10-27 18:00:58.234019','2024-10-27 18:00:58.234019','DEBIT_CARD',NULL,0),(6,NULL,'2024-10-27 18:03:28.895755','2024-10-27 18:03:28.895755','UPI','yashkalra@okaxis',0),(7,NULL,'2024-10-27 18:06:50.814093','2024-10-27 18:06:50.814093','UPI','9856011007@upi',0);
/*!40000 ALTER TABLE `PaymentDetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Restaurant`
--

DROP TABLE IF EXISTS `Restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Restaurant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `hoursOfOperation` varchar(255) NOT NULL,
  `restaurantName` varchar(255) NOT NULL,
  `address_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqv32m3urxsfjse6opfhwumq4v` (`address_id`),
  UNIQUE KEY `UK22c22gdvl7ytyrp7blgbx50hn` (`user_id`),
  CONSTRAINT `FK8i5m1l2w0etkuaeh6l3c6926w` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKggm3momemeke04br3yroi9dbg` FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Restaurant`
--

LOCK TABLES `Restaurant` WRITE;
/*!40000 ALTER TABLE `Restaurant` DISABLE KEYS */;
INSERT INTO `Restaurant` VALUES (1,'8','Sagar Ratna',8,9),(2,'12','Punjab Grill',23,10),(3,'12','Pizza Hut',11,12),(4,'12','Nazeer Foods',20,13),(5,'11','99 Chinese Adda',19,14);
/*!40000 ALTER TABLE `Restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RestaurantDeliveryZone`
--

DROP TABLE IF EXISTS `RestaurantDeliveryZone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RestaurantDeliveryZone` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pinCode` varchar(255) DEFAULT NULL,
  `zoneName` varchar(255) DEFAULT NULL,
  `restaurant_owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp8exwsjyt2fg829brviuljgmv` (`restaurant_owner_id`),
  CONSTRAINT `FKp8exwsjyt2fg829brviuljgmv` FOREIGN KEY (`restaurant_owner_id`) REFERENCES `Restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RestaurantDeliveryZone`
--

LOCK TABLES `RestaurantDeliveryZone` WRITE;
/*!40000 ALTER TABLE `RestaurantDeliveryZone` DISABLE KEYS */;
INSERT INTO `RestaurantDeliveryZone` VALUES (1,'110017','SAKET',1),(2,'110017','MALVIYA NAGAR',1),(3,'110048','GK-I',1),(4,'110017','SAKET',2),(5,'110017','MALVIYA NAGAR',2),(6,'110048','GK-I',2),(7,'110048','GK-I',3),(8,'110062','Sangam Vihar',4),(9,'110044','Khanpur',4),(10,'110062','Madangir',4),(11,'110062','Sangam Vihar',5),(12,'110044','Khanpur',5),(13,'110062','Madangir',5);
/*!40000 ALTER TABLE `RestaurantDeliveryZone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RestaurantOpeningDetail`
--

DROP TABLE IF EXISTS `RestaurantOpeningDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RestaurantOpeningDetail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `closingTime` varchar(255) DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `openingTime` varchar(255) DEFAULT NULL,
  `restaurant_owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbgr41gh89agchdtlrj7uanr0v` (`restaurant_owner_id`),
  CONSTRAINT `FKbgr41gh89agchdtlrj7uanr0v` FOREIGN KEY (`restaurant_owner_id`) REFERENCES `Restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RestaurantOpeningDetail`
--

LOCK TABLES `RestaurantOpeningDetail` WRITE;
/*!40000 ALTER TABLE `RestaurantOpeningDetail` DISABLE KEYS */;
INSERT INTO `RestaurantOpeningDetail` VALUES (1,'12:00 PM','MON','4:00 PM',1),(2,'12:00 PM','MON','4:00 PM',1),(3,'12:00 PM','TUE','4:00 PM',1),(4,'12:00 PM','WED','4:00 PM',1),(5,'12:00 PM','MON','4:00 PM',1),(6,'12:00 PM','THU','4:00 PM',1),(7,'12:00 PM','FRI','4:00 PM',1),(8,'12:00 PM','MON','12:00 AM',2),(9,'12:00 PM','TUE','12:00 AM',2),(10,'12:00 PM','WED','12:00 AM',2),(11,'12:00 PM','THU','12:00 AM',2),(12,'12:00 PM','FRI','12:00 AM',2),(13,'11:00 PM','MON','11:00 AM',3),(14,'11:00 PM','TUE','11:00 AM',3),(15,'11:00 PM','WED','11:00 AM',3),(16,'11:00 PM','THU','11:00 AM',3),(17,'11:00 PM','FRI','11:00 AM',3),(18,'11:00 PM','MON','11:00 AM',4),(19,'11:00 PM','TUE','11:00 AM',4),(20,'11:00 PM','WED','11:00 AM',4),(21,'11:00 PM','THU','11:00 AM',4),(22,'11:00 PM','FRI','11:00 AM',4),(23,'10:00 PM','MON','11:00 AM',5),(24,'10:00 PM','TUE','11:00 AM',5),(25,'10:00 PM','WED','11:00 AM',5),(26,'10:00 PM','THU','11:00 AM',5),(27,'10:00 PM','FRI','11:00 AM',5),(28,'10:00 PM','SAT','11:00 AM',5),(29,'10:00 PM','SUN','11:00 AM',5);
/*!40000 ALTER TABLE `RestaurantOpeningDetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdDate` datetime(6) DEFAULT NULL,
  `lastLogin` datetime(6) DEFAULT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','CUSTOMER','DELIVERY_PERSONNEL','RESTAURANT_OWNER') NOT NULL,
  `status` enum('ACTIVATED','DEACTIVATED') NOT NULL,
  `username` varchar(255) NOT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjreodf78a7pl5qidfh43axdfb` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'2024-10-27 17:39:30.412302','2024-10-30 12:39:32.302070','2024-10-30 12:39:32.323127','$2a$10$9i/l3P4jQgix5NbMiklFHucodmn2Plpjs4Qv9z.7/39FFXWCTdW8K','ADMIN','ACTIVATED','admin',10),(2,'2024-10-27 17:54:48.374504','2024-10-30 11:57:00.124390','2024-10-30 11:57:00.129430','$2a$10$kjieteUQZHsfJAfDB2hJPuNN/IJoYvat647h/zUJ2VXd3Ig4Cpu0m','CUSTOMER','ACTIVATED','nakulk',4),(5,'2024-10-27 17:59:37.009802',NULL,'2024-10-27 17:59:37.009802','$2a$10$SuSqtdULUZxBH9apG/Yg2u1qV2UmYJbi6HgTft782VGDoNryH2Bv2','CUSTOMER','ACTIVATED','abhi_gupta',0),(6,'2024-10-27 18:00:58.235250',NULL,'2024-10-27 18:00:58.235250','$2a$10$TaqsUajAs3Qp7MRg8lyegOh1PSCJAjcViVoD2kkbmpbRbFWnTso9y','CUSTOMER','ACTIVATED','divya_bajaj',0),(7,'2024-10-27 18:03:28.897795',NULL,'2024-10-27 18:03:28.897795','$2a$10$Tg62MUPwTosdSBzmbAHhoe31cC9lCvI8qqCTAy5qcgnaGsmQLYlXu','CUSTOMER','ACTIVATED','yashkalra25',0),(8,'2024-10-27 18:06:50.815708',NULL,'2024-10-28 19:39:46.037474','$2a$10$/ma.Cq1w0GX6ryamFCRiQ.zTZX83kwWjxO6/lGmY75tFzgtHq3ftq','CUSTOMER','DEACTIVATED','priyanka_98s',1),(9,'2024-10-27 18:12:55.503075',NULL,'2024-10-27 18:12:55.503075','$2a$10$koGGhsqSZ8TzRGVi.7J7M.lvcMh.byKMGVb9ywg7OKCvux7aV/qEm','RESTAURANT_OWNER','ACTIVATED','sagarratna_lp',0),(10,'2024-10-27 18:15:47.461957','2024-10-30 12:08:21.377794','2024-10-30 12:08:21.381172','$2a$10$gdtX0kAXK5OQXEB2/cb35uBFO0kpHIC5KaCWBdNgSymb0quyAmOOe','RESTAURANT_OWNER','ACTIVATED','punjabgrill_cc',5),(12,'2024-10-27 18:20:08.539107',NULL,'2024-10-27 18:20:08.539107','$2a$10$.Jllpse/r92qykp17FSu1.N4M7EopZRJuq44we1SdEXazYAVtIluy','RESTAURANT_OWNER','ACTIVATED','pizza-hut.gk1',0),(13,'2024-10-27 18:25:29.296405','2024-10-28 20:44:32.191040','2024-10-28 20:44:32.192167','$2a$10$8t2FIv6A.Baz.JY9.rX.JeiHyoblE3pL5dhxBIffFmVN1YAkC9FeS','RESTAURANT_OWNER','ACTIVATED','nazeer.foods',2),(14,'2024-10-27 18:29:25.696581','2024-10-28 20:06:57.967457','2024-10-28 20:30:56.571102','$2a$10$Mx7ssDJ4NL581vG30QNUWOKP2ikmhJ6l42B9nd3erkVsv5qhHr7by','RESTAURANT_OWNER','ACTIVATED','chinese.adda',2),(15,'2024-10-27 18:44:52.892576','2024-10-30 12:23:38.350772','2024-10-30 12:23:38.352474','$2a$10$r.5I2/DkYioEzdU3jfhK1eZ7HROYVmhDi/7P.AqsvJ/4jZgg9ItSG','DELIVERY_PERSONNEL','ACTIVATED','deliveryboy-rakesh',15),(16,'2024-10-27 18:46:06.414788',NULL,'2024-10-27 18:46:06.414788','$2a$10$Mbc9U6DP.uJrb9Ue2h81UeEm5FlnYnnHmolRFNPtfO.FgvVQRl4.a','DELIVERY_PERSONNEL','ACTIVATED','deliveryboy-ramesh',0),(17,'2024-10-27 18:46:46.481176',NULL,'2024-10-27 18:46:46.481176','$2a$10$KKX/8ouXkfGW5RN.0SUyPuGkk.ebZnxp9SQELgCmg9fVoACYt3JuK','DELIVERY_PERSONNEL','ACTIVATED','deliveryboy-taneja',0);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-01 10:34:38
