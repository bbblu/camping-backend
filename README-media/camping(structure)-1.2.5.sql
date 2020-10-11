-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: camping
-- ------------------------------------------------------
-- Server version	8.0.15

DROP DATABASE IF EXISTS camping;

CREATE DATABASE IF NOT EXISTS camping
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE camping;

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
-- Temporary view structure for view `can_borrow_product_group`
--

DROP TABLE IF EXISTS `can_borrow_product_group`;
/*!50001 DROP VIEW IF EXISTS `can_borrow_product_group`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8mb4;
/*!50001 CREATE VIEW `can_borrow_product_group` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `cover_image`,
 1 AS `price`,
 1 AS `borrow_start_date`,
 1 AS `borrow_end_date`,
 1 AS `city`,
 1 AS `city_name`,
 1 AS `city_area_name`,
 1 AS `user_name`,
 1 AS `product_type`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `city` (
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '城市名稱，如臺北市、宜蘭縣',
  `area_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '區名稱，如中正區、宜蘭市',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否啟用(0: 不啟用/ 1: 啟用)',
  PRIMARY KEY (`name`,`area_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='可租借城市';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `log_record`
--

DROP TABLE IF EXISTS `log_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `log_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `server_version` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '伺服器版本',
  `ip` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用者IPv4',
  `method` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '發送請求時的方法',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '網址',
  `executor` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用者帳號',
  `execute_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作日期',
  `device` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用設備(-1: Unknown/ 00: Postman/ 01: 瀏覽器/ 02: App瀏覽器/ 03: App)',
  `device_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '設備類型(-1: Unknown/ 00: Postman/ 01: IE/ 02: Edge/ 03: Chrome/ 04:FireFox/ 05: Safari/ 06: Opera/ 07: Android/ 08: iOS)',
  `device_version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '設備版本',
  `result` tinyint(4) NOT NULL COMMENT '執行結果',
  `error_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '錯誤代碼',
  `message` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '回應訊息',
  PRIMARY KEY (`id`),
  KEY `fk_log_record_executor_idx` (`executor`),
  CONSTRAINT `fk_log_record_executor` FOREIGN KEY (`executor`) REFERENCES `user` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='使用者操作紀錄';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `problem_report`
--

DROP TABLE IF EXISTS `problem_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `problem_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '問題類型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '狀態(0: 未處理/ 1: 處理中/ 2: 已完成)',
  `reporter_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '回報者信箱',
  `report_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回報日期',
  `report_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '回報內容',
  `handler` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '處理人帳號',
  `handle_result` text COLLATE utf8mb4_unicode_ci COMMENT '處理結果',
  `handle_date` datetime DEFAULT NULL COMMENT '處理時間',
  `last_modify_account` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最後修改人帳號',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最後修改日期',
  PRIMARY KEY (`id`),
  KEY `fk_problem_report_idx` (`handler`),
  KEY `fk_problem_report_modify_id_idx` (`last_modify_account`),
  CONSTRAINT `fk_problem_report_handler` FOREIGN KEY (`handler`) REFERENCES `user` (`account`),
  CONSTRAINT `fk_problem_report_modify_id` FOREIGN KEY (`last_modify_account`) REFERENCES `user` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='問題回報';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `group_id` int(11) unsigned NOT NULL COMMENT '商品群組編號',
  `enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否啟用(0: 否/ 1: 是)',
  `type` int(11) unsigned NOT NULL COMMENT '商品類型',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名稱',
  `count` int(11) NOT NULL COMMENT '數量',
  `brand` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '品牌',
  `appearance` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '外觀狀況',
  `use_information` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用方式',
  `broken_compensation` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '損壞賠償',
  `related_link` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '相關連結',
  `memo` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '備註',
  `last_modify_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最後修改者帳號',
  `last_modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後修改時間',
  PRIMARY KEY (`id`),
  KEY `fk_product_group_id_idx` (`group_id`),
  KEY `fk_product_type_idx` (`type`),
  KEY `fk_product_last_modify_account_idx` (`last_modify_account`),
  CONSTRAINT `fk_product_group_id` FOREIGN KEY (`group_id`) REFERENCES `product_group` (`id`),
  CONSTRAINT `fk_product_last_modify_account` FOREIGN KEY (`last_modify_account`) REFERENCES `user` (`account`),
  CONSTRAINT `fk_product_type` FOREIGN KEY (`type`) REFERENCES `product_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='上架商品的詳細內容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_group`
--

DROP TABLE IF EXISTS `product_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否啟用(0: 否/ 1: 是)',
  `bank_account` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '匯款帳戶',
  `name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品群組名稱',
  `cover_image` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '封面圖',
  `city_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '城市名稱，如臺北市、宜蘭縣',
  `city_area_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '區名稱，如中正區、宜蘭市',
  `price` int(11) NOT NULL COMMENT '租借價格',
  `borrow_start_date` datetime NOT NULL COMMENT '可租借的起始時間',
  `borrow_end_date` datetime NOT NULL COMMENT '可租借的結束時間',
  `create_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品上架者帳號',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品上架時間',
  `last_modify_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最後修改者的帳號',
  `last_modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後修改時間',
  PRIMARY KEY (`id`),
  KEY `fk_product_group_create_account_idx` (`create_account`),
  KEY `fk_product_group_last_modify_account_idx` (`last_modify_account`),
  KEY `fk_product_group_city_idx` (`city_name`,`city_area_name`),
  CONSTRAINT `fk_product_group_city` FOREIGN KEY (`city_name`, `city_area_name`) REFERENCES `city` (`name`, `area_name`),
  CONSTRAINT `fk_product_group_create_account` FOREIGN KEY (`create_account`) REFERENCES `user` (`account`),
  CONSTRAINT `fk_product_group_last_modify_account` FOREIGN KEY (`last_modify_account`) REFERENCES `user` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='上架商品群組';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_group_comment`
--

DROP TABLE IF EXISTS `product_group_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_group_comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `group_id` int(10) unsigned NOT NULL COMMENT '商品群組編號',
  `comment` tinyint(6) unsigned NOT NULL COMMENT '評價(0 ~ 5)',
  `comment_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '評價者帳號',
  `comment_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '評價時間',
  PRIMARY KEY (`id`),
  KEY `fk_product_group_comment_group_id_idx` (`group_id`),
  KEY `fk_product_group_comment_comment_account_idx` (`comment_account`),
  CONSTRAINT `fk_product_group_comment_comment_account` FOREIGN KEY (`comment_account`) REFERENCES `user` (`account`),
  CONSTRAINT `fk_product_group_comment_group_id` FOREIGN KEY (`group_id`) REFERENCES `product_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品群組評價';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_image` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `product_id` int(10) unsigned NOT NULL COMMENT '商品編號',
  `enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否啟用(0: 否/ 1: 是)',
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '圖片網址',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
  `last_modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後修改時間',
  PRIMARY KEY (`id`),
  KEY `product_image_product_id_idx` (`product_id`),
  CONSTRAINT `product_image_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品圖';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_launched_process`
--

DROP TABLE IF EXISTS `product_launched_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_launched_process` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `group_id` int(10) unsigned NOT NULL COMMENT '要上架的商品群組編號',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '執行狀態(0: 未執行/ 1: 已執行/ 2: 失敗)',
  `launched_date` datetime NOT NULL COMMENT '上架時間',
  `error_memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '錯誤紀錄',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
  `last_modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後修改時間',
  PRIMARY KEY (`id`),
  KEY `fk_product_launched_process_group_id_idx` (`group_id`),
  CONSTRAINT `fk_product_launched_process_group_id` FOREIGN KEY (`group_id`) REFERENCES `product_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品群組上架紀錄表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_type`
--

DROP TABLE IF EXISTS `product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '類型名稱',
  `enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否啟用(0: 否/ 1: 是)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品細項類型對照表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rental_detail`
--

DROP TABLE IF EXISTS `rental_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rental_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `record_id` int(10) unsigned NOT NULL COMMENT '租借紀錄編號',
  `product_id` int(10) unsigned NOT NULL COMMENT '商品編號',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '狀態(0: 未歸還/ 1: 已歸還/ 2: 損壞/ 3: 遺失)',
  `check_memo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '出借方檢查備註',
  `last_modify_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最後修改者帳號',
  `last_modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後修改時間',
  PRIMARY KEY (`id`),
  KEY `fk_rental_detail_record_id_idx` (`record_id`),
  KEY `fk_rental_detail_product_id_idx` (`product_id`),
  KEY `fk_rental_detail_last_modify_account_idx` (`last_modify_account`),
  CONSTRAINT `fk_rental_detail_last_modify_account` FOREIGN KEY (`last_modify_account`) REFERENCES `user` (`account`),
  CONSTRAINT `fk_rental_detail_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_rental_detail_record_id` FOREIGN KEY (`record_id`) REFERENCES `rental_record` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租借詳細記錄';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rental_record`
--

DROP TABLE IF EXISTS `rental_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rental_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `product_group_id` int(10) unsigned NOT NULL COMMENT '租借商品群組編號',
  `enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否啟用(0: 否/ 1: 是)',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '狀態(0:取消/ 1: 未取貨/ 2:未歸還/ 3:已歸還/ 4: 已檢查)',
  `transaction_id` int(11) NOT NULL COMMENT '信用卡交易編號',
  `renter_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租借者帳號',
  `renter_credit_card_id` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租借人的信用卡號(只顯示末四碼)',
  `rental_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '點選我要租借的時間，即建立時間',
  `borrow_start_date` datetime NOT NULL COMMENT '預計租借起始時間',
  `borrow_end_date` datetime NOT NULL COMMENT '預計租借結束時間',
  `pick_date` datetime DEFAULT NULL COMMENT '取貨時間',
  `return_date` datetime DEFAULT NULL COMMENT '歸還時間',
  `check_date` datetime DEFAULT NULL COMMENT '檢查時間',
  `cancel_date` datetime DEFAULT NULL COMMENT '取消時間',
  `cancel_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '取消原因',
  `last_modify_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最後修改人帳號',
  `last_modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後更新時間',
  PRIMARY KEY (`id`),
  KEY `fk_rental_record_idx` (`product_group_id`),
  KEY `fk_rental_record_renter_account_idx` (`renter_account`),
  KEY `fk_rental_record_last_modify_account_idx` (`last_modify_account`),
  CONSTRAINT `fk_rental_record_last_modify_account` FOREIGN KEY (`last_modify_account`) REFERENCES `user` (`account`),
  CONSTRAINT `fk_rental_record_product_group_id` FOREIGN KEY (`product_group_id`) REFERENCES `product_group` (`id`),
  CONSTRAINT `fk_rental_record_renter_account` FOREIGN KEY (`renter_account`) REFERENCES `user` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租借紀錄';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用者帳號',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用者密碼',
  `role_id` int(11) unsigned NOT NULL DEFAULT '3' COMMENT '使用者權限(3為一般使用者)',
  `enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否啟用(0: 否/ 1: 是)',
  `experience` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '露營經驗(0: 新手/ 1: 有過幾次經驗)',
  `last_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用者姓氏',
  `first_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用者名稱',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用者暱稱',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用者性別(0: 男/ 1: 女/ 2: 未提供)',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '電子信箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '使用者住址',
  `birthday` date NOT NULL COMMENT '生日',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
  `last_modify_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最後修改者帳號',
  `last_modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後修改時間',
  PRIMARY KEY (`account`),
  KEY `fk_user_role_id_idx` (`role_id`),
  KEY `fk_user_modify_id` (`last_modify_account`),
  CONSTRAINT `fk_user_last_modify_account` FOREIGN KEY (`last_modify_account`) REFERENCES `user` (`account`),
  CONSTRAINT `fk_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='使用者';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_comment`
--

DROP TABLE IF EXISTS `user_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `user_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '被評價的使用者帳號',
  `comment` tinyint(6) unsigned NOT NULL COMMENT '評價(0 ~ 5)',
  `comment_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '評價者帳號',
  `comment_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '評價時間',
  PRIMARY KEY (`id`),
  KEY `fk_user_comment_comment_account_idx` (`comment_account`),
  KEY `fk_user_comment_user_account_idx` (`user_account`),
  CONSTRAINT `fk_user_comment_comment_account` FOREIGN KEY (`comment_account`) REFERENCES `user` (`account`),
  CONSTRAINT `fk_user_comment_user_account` FOREIGN KEY (`user_account`) REFERENCES `user` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='使用者評價';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水編號',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '權限名稱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='使用者權限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `can_borrow_product_group`
--

/*!50001 DROP VIEW IF EXISTS `can_borrow_product_group`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `can_borrow_product_group` AS select `group`.`id` AS `id`,`group`.`name` AS `name`,`group`.`cover_image` AS `cover_image`,`group`.`price` AS `price`,`group`.`borrow_start_date` AS `borrow_start_date`,`group`.`borrow_end_date` AS `borrow_end_date`,concat(`group`.`city_name`,' ',`group`.`city_area_name`) AS `city`,`group`.`city_name` AS `city_name`,`group`.`city_area_name` AS `city_area_name`,concat(`user`.`account`,'(',`user`.`nick_name`,')') AS `user_name`,group_concat(`product`.`type` separator ',') AS `product_type` from (((`product_group` `group` left join `product` on((`group`.`id` = `product`.`group_id`))) left join `rental_record` `record` on((`group`.`id` = `record`.`product_group_id`))) join `user` on((`group`.`create_account` = `user`.`account`))) where ((`group`.`enable` = 1) and (isnull(`record`.`id`) or (`record`.`enable` = 0) or (`record`.`status` = '4'))) group by `product`.`group_id` order by `group`.`create_date` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-01 23:31:07
