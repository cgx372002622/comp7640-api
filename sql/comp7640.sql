-- comp7640.`user` definition

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `contact_number` bigint DEFAULT NULL,
  `shipping_details` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_unique` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- comp7640.vendor definition

CREATE TABLE `vendor` (
  `vendor_id` int NOT NULL AUTO_INCREMENT,
  `business_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `vendor_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `score` float DEFAULT NULL,
  `geographical_presence` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`vendor_id`),
  UNIQUE KEY `vendor_unique` (`vendor_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- comp7640.product definition

CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `listed_price` decimal(10,0) NOT NULL,
  `tags` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `inventory` int NOT NULL,
  `vendor_id` int NOT NULL,
  `img_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `product_vendor_FK` (`vendor_id`),
  CONSTRAINT `product_vendor_FK` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- comp7640.`transaction` definition

CREATE TABLE `transaction` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `amount` int NOT NULL,
  `status` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `transaction_user_FK` (`user_id`),
  KEY `transaction_product_FK` (`product_id`),
  CONSTRAINT `transaction_product_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `transaction_user_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;