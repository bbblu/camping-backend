-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: camping
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES ('台北市','中山區',1),('台北市','中正區',1),('台北市','信義區',1),('台北市','內湖區',1),('台北市','北投區',1),('台北市','南港區',1),('台北市','士林區',1),('台北市','大同區',1),('台北市','大安區',1),('台北市','文山區',1),('台北市','松山區',1),('台北市','萬華區',1),('新北市','三峽區',1),('新北市','三芝區',1),('新北市','三重區',1),('新北市','中和區',1),('新北市','五股區',1),('新北市','八里區',1),('新北市','土城區',1),('新北市','坪林區',1),('新北市','平溪區',1),('新北市','新店區',1),('新北市','新莊區',1),('新北市','板橋區',1),('新北市','林口區',1),('新北市','樹林區',1),('新北市','永和區',1),('新北市','汐止區',1),('新北市','泰山區',1),('新北市','淡水區',1),('新北市','深坑區',1),('新北市','烏來區',1),('新北市','瑞芳區',1),('新北市','石碇區',1),('新北市','石門區',1),('新北市','萬里區',1),('新北市','蘆洲區',1),('新北市','貢寮區',1),('新北市','金山區',1),('新北市','雙溪區',1),('新北市','鶯歌區',1);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `problem_report`
--

LOCK TABLES `problem_report` WRITE;
/*!40000 ALTER TABLE `problem_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `problem_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_group`
--

LOCK TABLES `product_group` WRITE;
/*!40000 ALTER TABLE `product_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_group_comment`
--

LOCK TABLES `product_group_comment` WRITE;
/*!40000 ALTER TABLE `product_group_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_group_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_launched_process`
--

LOCK TABLES `product_launched_process` WRITE;
/*!40000 ALTER TABLE `product_launched_process` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_launched_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_type`
--

LOCK TABLES `product_type` WRITE;
/*!40000 ALTER TABLE `product_type` DISABLE KEYS */;
INSERT INTO `product_type` VALUES (1,'桌子',1),(2,'椅子',1),(3,'睡袋',1),(4,'睡墊',1),(5,'睡帳',1),(6,'營燈',1),(7,'客廳帳',1),(8,'天幕',1),(9,'其他工具',1),(10,'餐廚用具',1);
/*!40000 ALTER TABLE `product_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rental_detail`
--

LOCK TABLES `rental_detail` WRITE;
/*!40000 ALTER TABLE `rental_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `rental_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rental_record`
--

LOCK TABLES `rental_record` WRITE;
/*!40000 ALTER TABLE `rental_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `rental_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rental_record_cancel`
--

LOCK TABLES `rental_record_cancel` WRITE;
/*!40000 ALTER TABLE `rental_record_cancel` DISABLE KEYS */;
/*!40000 ALTER TABLE `rental_record_cancel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','$2a$10$Zl7XMD5wB4qD8JfJYBZayOjOyDs8Vfyz64qzSnm0IfgXJXHdr4jU6',1,1,'0','系統','管理員','管理員','0','10646007@ntub.edu.tw','台北市中正區濟南路321號','2020-07-12','2020-07-12 16:00:37','admin','2020-10-11 15:45:18'),('anonymousUser','123',3,0,'0','訪','客','訪客','0','10646007@ntub.edu.tw','台北市中正區濟南路321號','2020-07-11','2020-08-11 20:02:17','anonymousUser','2020-08-13 19:25:31');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_comment`
--

LOCK TABLES `user_comment` WRITE;
/*!40000 ALTER TABLE `user_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'administrator'),(2,'manager'),(3,'user'),(4,'renter');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-11 16:44:46
