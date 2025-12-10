SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- book表
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
                         `book_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '图书编号',
                         `book_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名称',
                         `book_isbn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书标准ISBN编号',
                         `book_press` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书出版社',
                         `book_author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书作者',
                         `book_pagination` int(11) NULL DEFAULT NULL COMMENT '图书页数',
                         `book_price` double(10, 0) NULL DEFAULT NULL COMMENT '图书价格',
                         `book_uploadtime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书上架时间',
                         `book_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '图书状态（0：可借阅，1:已借阅，2：归还中，3：已下架）',
                         `book_borrower` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅人',
                         `book_borrowtime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅时间',
                         `book_returntime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书预计归还时间',
                         PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- book记录
-- ----------------------------
INSERT INTO `book` VALUES (1, 'Java基础案例教程（第2版）', '9787115547477', '人民邮电出版社', '传智播客', 291, 59, '2025-12-07', '3', NULL, NULL, NULL);
INSERT INTO `book` VALUES (2, '挪威的森林', '9787546205618', '上海译文出版社', '村上春树', 380, 34, '2025-12-07', '1', '杨子翔', '2025-12-07', '2026-12-07');
INSERT INTO `book` VALUES (3, '离散数学（第三版）', '9787560621579', '西安电子科技大学出版社', '方世昌', 328, 35, '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (4, 'JavaWeb程序设计任务教程', '9787115439369', '人民邮电出版社', '传智播客', 419, 56, '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (5, 'Java基础入门（第2版）', '9787302511410', '清华大学出版社', '传智播客', 413, 59, '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (6, 'SpringCloud微服务架构开发', '9787115529046', '人民邮电出版社', '传智播客', 196, 43, '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (7, 'SpringBoot企业级开发教程', '9787115512796', '人民邮电出版社', '传智播客', 270, 56, '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (8, '围城', '9787020127894', '人民文学出版社', '钱钟书', 382, 25, '2025-12-07', '0', '', NULL, NULL);
INSERT INTO `book` VALUES (9, 'Spark大数据分析与实战', '9787302534327', '清华大学出版社', '传智播客', 228, 49, '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (10, '边城', '9787543067028', '武汉出版社', '沈从文', 368, 26, '2025-12-07', '0', '', NULL, NULL);
INSERT INTO `book` VALUES (11, '自在独行', '9787535488473', '长江文艺出版社', '贾平凹', 320, 39, '2025-12-07', '3', NULL, NULL, NULL);
INSERT INTO `book` VALUES (12, '沉默的巡游', '9787544280662', '南海出版公司', '[日]东野圭吾', 400, 59, '2025-12-07', '0', '', NULL, NULL);
INSERT INTO `book` VALUES (13, '计算机组成原理（第3版）', '9787040545180', '高等教育出版社', '唐朔飞', 428, 50, '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (14, '青春猪头少年不会梦到兔女郎学姐', '9787544290660', '湖南美术出版社', '[日]鸭志田一', 296, 39.80 , '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (15, '我的青春恋爱物语果然有问题', '9787559102809', '安徽少年儿童出版社', ' [日]渡航', 224, 28, '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (16, '福尔摩斯探案集（人民文学出版社经典译本）', '9787020006168', '人民文学出版社', ' [英]阿瑟·柯南·道尔', 1504, 68, '2025-12-07', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (17, '白夜行', '9787544258609', '南海出版公司', '[日]东野圭吾', 538, 59.60, '2025-12-07', '0', NULL, NULL, NULL);

-- ----------------------------
-- record表
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
                           `record_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '借阅记录id',
                           `record_bookname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '借阅的图书名称',
                           `record_bookisbn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '借阅的图书的ISBN编号',
                           `record_borrower` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅人',
                           `record_borrowtime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅时间',
                           `record_remandtime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书归还时间',
                           PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- record记录
-- ----------------------------
INSERT INTO `record` VALUES (23, 'Java基础案例教程（第2版）', '9787115547477', '杨子翔', '2025-12-07', '2025-12-08');
INSERT INTO `record` VALUES (24, 'Spark大数据分析与实战', '9787302534327', '杨子翔', '2025-12-07', '2025-12-08');
INSERT INTO `record` VALUES (26, '边城', '9787543067028', '杨子翔', '2025-12-07', '2025-12-08');

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `user_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '用户id',
                         `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
                         `user_salt` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码盐值',
                         `user_hash` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码哈希值',
                         `user_email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱（用户账号）',
                         `user_createdate` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户注册时间',
                         `user_role` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户角色',
                         `user_deletedate` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户注销时间',
                         `user_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户状态（0:正常,1:禁用）',
                         PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- 用户记录
-- ----------------------------
-- ----------------------------
-- 密码：
-- 杨子翔 yzx
-- 邹循 zx
-- 万之现 wzx
-- 王成 wc
-- 杨顺东 ysd
-- 李民庆 lmq
-- 一号用户 1
-- 二号用户 2
-- ---------------------------
INSERT INTO `user` VALUES (1, '杨子翔', 'j0lMfoBkUxPXDh/p6EMU/A==','f0199c162fc77cc44c6e2a2dbd2f719e56b31e6c1de6f654156829cf002abcbc' ,'yzxyzx', '2025-12-07', 'ADMIN', NULL, '0');
INSERT INTO `user` VALUES (2, '邹循', 'dIHqZwJWP/InD5ztte0zrQ==','a663a47bee3b9ca2bbfdf9ac52cc6d4be8aaa096ba39a1f8e694ef2a927be678' ,'zxzx', '2025-12-07', 'ADMIN', NULL, '0');
INSERT INTO `user` VALUES (3, '万之现', '/7fsNhhSJJRm12K7v9ULGQ==','917f341d1a901a12c63d9719ffcfb368f1b9d03f9a9315754d4eed663486a803' ,'wzxwzx', '2025-12-07', 'ADMIN', NULL, '0');
INSERT INTO `user` VALUES (4, '王成', 'FcHpua94D0xwHLQxWMkMkQ==','5324762092842e56d1c9c427d9d565c861c8316422c134119dc7a82ecd51ed40' ,'wcwc', '2025-12-07', 'ADMIN', NULL, '0');
INSERT INTO `user` VALUES (5, '杨顺东', 'QJ4MVNsm+p4dvkKQDDUSFQ==','8963ec4528803c3249d111ba360128a489fd6e3dd88d13facbcc28b7f72d8989' ,'ysdysd', '2025-12-07', 'ADMIN', NULL, '0');
INSERT INTO `user` VALUES (6, '李民庆', 'HKmB4iqSPJkvWqtanB9yNw==','ac675bf3295f8a90163ea3e54a7988cd9441a5a8615f6ca2fb7d98fa755c68a4' ,'lmqlmq', '2025-12-07', 'ADMIN', NULL, '0');
INSERT INTO `user` VALUES (7, '一号用户', 'R5jtTu+dmXkjWRAbXSOP6g==','b03a58cf15b6ced7f0b8fc3e5f0b9f4a0d1e9a10f957bbc67fc1b198349c51df' ,'1u', '2025-12-07', 'USER', '2025-12-07', '1');
INSERT INTO `user` VALUES (8, '二号用户', '4IKv3nkTKkw6qbP2czQD1w==','a2d872495c31f8ee808d931ef63aff21f9910d880a03304e440e1fdcab9f478d' ,'2u', '2025-12-07', 'USER', NULL, '0');

SET FOREIGN_KEY_CHECKS = 1;
