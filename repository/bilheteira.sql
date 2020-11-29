SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for actor
-- ----------------------------
DROP TABLE IF EXISTS `actor`;
CREATE TABLE `actor`  (
  `actorName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`actorName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for favourite
-- ----------------------------
DROP TABLE IF EXISTS `favourite`;
CREATE TABLE `favourite`  (
  `user` int(255) NOT NULL,
  `film` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`user`) USING BTREE,
  INDEX `film`(`film`) USING BTREE,
  CONSTRAINT `favourite_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favourite_ibfk_2` FOREIGN KEY (`film`) REFERENCES `film` (`movieId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for film
-- ----------------------------
DROP TABLE IF EXISTS `film`;
CREATE TABLE `film`  (
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `movieId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `year` int(255) NOT NULL,
  `released` datetime(6) NOT NULL,
  `plot` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `runtime` int(255) NOT NULL,
  `director` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `like` int(255) NULL DEFAULT 0,
  `rating` double(255, 0) NULL DEFAULT 0,
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`movieId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for genre
-- ----------------------------
DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre`  (
  `genreName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`genreName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for moviebytype
-- ----------------------------
DROP TABLE IF EXISTS `moviebytype`;
CREATE TABLE `moviebytype`  (
  `genreName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `movieId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`genreName`) USING BTREE,
  INDEX `movieId`(`movieId`) USING BTREE,
  CONSTRAINT `moviebytype_ibfk_1` FOREIGN KEY (`genreName`) REFERENCES `genre` (`genreName`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `moviebytype_ibfk_2` FOREIGN KEY (`movieId`) REFERENCES `film` (`movieId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `cinema` int(255) NOT NULL,
  `user` int(255) NOT NULL,
  `date` datetime(0) NOT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cinema`(`cinema`) USING BTREE,
  INDEX `user`(`user`) USING BTREE,
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`cinema`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userid` int(255) NOT NULL,
  `ticket` int(255) NOT NULL,
  `date` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ticket`(`ticket`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`ticket`) REFERENCES `ticket` (`ticketId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `payment_ibfk_3` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `cinema` int(255) NOT NULL,
  `ticket` int(255) NOT NULL,
  PRIMARY KEY (`cinema`) USING BTREE,
  INDEX `ticket`(`ticket`) USING BTREE,
  CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`cinema`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `stock_ibfk_2` FOREIGN KEY (`ticket`) REFERENCES `ticket` (`ticketId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `ticketId` int(255) NOT NULL AUTO_INCREMENT,
  `film` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` double(255, 2) NOT NULL,
  `sold` int(255) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ticketId`) USING BTREE,
  INDEX `film`(`film`) USING BTREE,
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`film`) REFERENCES `film` (`movieId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userEmail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` int(255) NOT NULL,
  `flag` int(255) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
