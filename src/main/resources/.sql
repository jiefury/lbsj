CREATE TABLE `base_file` (
  `id` bigint NOT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `suffix` varchar(255) DEFAULT NULL,
  `deleted` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;