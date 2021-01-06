/*
 Navicat MySQL Data Transfer

 Source Server         : 我的连接
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : bilheteira

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 05/01/2021 19:18:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for actor
-- ----------------------------
DROP TABLE IF EXISTS `actor`;
CREATE TABLE `actor`  (
  `actor_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`actor_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cinema
-- ----------------------------
DROP TABLE IF EXISTS `cinema`;
CREATE TABLE `cinema`  (
  `id` int(255) NOT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `followers` int(255) NOT NULL DEFAULT 0,
  `solds` int(255) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `cinema_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  `author` int(255) NOT NULL,
  `cinema` int(255) NOT NULL,
  `film` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `premier` int(255) NOT NULL,
  `replyto` int(255) NOT NULL,
  `likes` int(255) NOT NULL DEFAULT 0,
  `flag` tinyint(255) NOT NULL DEFAULT 0,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `author`(`author`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  INDEX `cinema`(`cinema`) USING BTREE,
  INDEX `film`(`film`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country`  (
  `country_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`country_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for favouritecinema
-- ----------------------------
DROP TABLE IF EXISTS `favouritecinema`;
CREATE TABLE `favouritecinema`  (
  `user` int(255) NOT NULL,
  `cinema` int(255) NOT NULL,
  PRIMARY KEY (`user`, `cinema`) USING BTREE,
  INDEX `cinema`(`cinema`) USING BTREE,
  CONSTRAINT `favouritecinema_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favouritecinema_ibfk_2` FOREIGN KEY (`cinema`) REFERENCES `cinema` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for favouritefilm
-- ----------------------------
DROP TABLE IF EXISTS `favouritefilm`;
CREATE TABLE `favouritefilm`  (
  `user` int(255) NOT NULL,
  `film` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`user`, `film`) USING BTREE,
  INDEX `film`(`film`) USING BTREE,
  CONSTRAINT `favouritefilm_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favouritefilm_ibfk_2` FOREIGN KEY (`film`) REFERENCES `film` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for film
-- ----------------------------
DROP TABLE IF EXISTS `film`;
CREATE TABLE `film`  (
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `movie_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `plot` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `runtime` int(255) NOT NULL,
  `director` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `likes` int(255) NOT NULL DEFAULT 0,
  `rating` double(255, 0) NOT NULL DEFAULT 0,
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `year` int(255) NOT NULL,
  `released` date NOT NULL,
  PRIMARY KEY (`movie_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for filmbycountry
-- ----------------------------
DROP TABLE IF EXISTS `filmbycountry`;
CREATE TABLE `filmbycountry`  (
  `country_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `film` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`country_name`, `film`) USING BTREE,
  INDEX `film`(`film`) USING BTREE,
  CONSTRAINT `filmbycountry_ibfk_1` FOREIGN KEY (`country_name`) REFERENCES `country` (`country_name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `filmbycountry_ibfk_2` FOREIGN KEY (`film`) REFERENCES `film` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for filmbygenre
-- ----------------------------
DROP TABLE IF EXISTS `filmbygenre`;
CREATE TABLE `filmbygenre`  (
  `genre_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `film` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`genre_name`, `film`) USING BTREE,
  INDEX `genre_name`(`genre_name`) USING BTREE,
  INDEX `film_id`(`film`) USING BTREE,
  CONSTRAINT `filmbygenre_ibfk_1` FOREIGN KEY (`film`) REFERENCES `film` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `filmbygenre_ibfk_2` FOREIGN KEY (`genre_name`) REFERENCES `genre` (`genre_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for genre
-- ----------------------------
DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre`  (
  `genre_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`genre_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `sender` int(255) NOT NULL,
  `receiver` int(255) NOT NULL,
  `created` datetime(0) NOT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `read` tinyint(255) NOT NULL DEFAULT 0,
  `flag` tinyint(255) NOT NULL DEFAULT 0,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `data` int(255) NOT NULL DEFAULT 0,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sender`(`sender`) USING BTREE,
  INDEX `receiver`(`receiver`) USING BTREE,
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `buyer` int(255) NOT NULL,
  `ticket` int(255) NOT NULL,
  `date` datetime(0) NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ticket`(`ticket`) USING BTREE,
  INDEX `userid`(`buyer`) USING BTREE,
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`ticket`) REFERENCES `ticket` (`ticket_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `payment_ibfk_3` FOREIGN KEY (`buyer`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for premier
-- ----------------------------
DROP TABLE IF EXISTS `premier`;
CREATE TABLE `premier`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `film` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cinema` int(255) NOT NULL,
  `start` date NOT NULL,
  `end` date NOT NULL,
  `price` double(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `film`(`film`) USING BTREE,
  INDEX `cinema`(`cinema`) USING BTREE,
  CONSTRAINT `premier_ibfk_1` FOREIGN KEY (`film`) REFERENCES `film` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `premier_ibfk_2` FOREIGN KEY (`cinema`) REFERENCES `cinema` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for registerrequest
-- ----------------------------
DROP TABLE IF EXISTS `registerrequest`;
CREATE TABLE `registerrequest`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created` datetime(0) NOT NULL,
  `processed` tinyint(255) NOT NULL DEFAULT 0,
  `accepted` tinyint(255) NOT NULL DEFAULT 0,
  `id` int(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` int(255) NOT NULL,
  `seats` int(255) NOT NULL,
  `cinema` int(255) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cinema`(`cinema`) USING BTREE,
  INDEX `room_id`(`id`) USING BTREE,
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`cinema`) REFERENCES `cinema` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `premier` int(255) NOT NULL,
  `start` datetime(0) NOT NULL,
  `end` datetime(0) NOT NULL,
  `room` int(255) NOT NULL,
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `solds` int(255) NOT NULL DEFAULT 0,
  PRIMARY KEY (`premier`, `start`, `end`, `room`) USING BTREE,
  INDEX `room`(`room`) USING BTREE,
  INDEX `uuid`(`uuid`) USING BTREE,
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`premier`) REFERENCES `premier` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`room`) REFERENCES `room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for seat
-- ----------------------------
DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat`  (
  `seat_id` int(255) NOT NULL,
  `room_id` int(255) NOT NULL,
  `row` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `column` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`seat_id`) USING BTREE,
  INDEX `room_id`(`room_id`) USING BTREE,
  CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for starredin
-- ----------------------------
DROP TABLE IF EXISTS `starredin`;
CREATE TABLE `starredin`  (
  `actor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `film` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `personage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`actor`, `film`) USING BTREE,
  INDEX `film`(`film`) USING BTREE,
  CONSTRAINT `starredin_ibfk_1` FOREIGN KEY (`actor`) REFERENCES `actor` (`actor_name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `starredin_ibfk_2` FOREIGN KEY (`film`) REFERENCES `film` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `ticket_id` int(255) NOT NULL AUTO_INCREMENT,
  `room_id` int(255) NULL DEFAULT NULL,
  `schedule` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` double(255, 2) NOT NULL,
  `sold` tinyint(255) NOT NULL DEFAULT 0,
  `seat_id` int(255) NOT NULL,
  PRIMARY KEY (`ticket_id`) USING BTREE,
  INDEX `room_id`(`room_id`) USING BTREE,
  INDEX `seat_id`(`seat_id`) USING BTREE,
  INDEX `ticket_ibfk_5`(`schedule`) USING BTREE,
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ticket_ibfk_3` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`seat_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ticket_ibfk_5` FOREIGN KEY (`schedule`) REFERENCES `schedule` (`uuid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` int(255) NOT NULL,
  `flag` int(255) NOT NULL DEFAULT 0,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `email`(`user_email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
