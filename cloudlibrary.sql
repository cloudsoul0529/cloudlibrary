SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ========================================================
-- user (用户信息管理)
-- ========================================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `user_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_name` VARCHAR(32) NOT NULL COMMENT '用户名',
    `user_password_hash` VARCHAR(32) NOT NULL COMMENT '密码哈希值(加盐后)',
    `user_password_salt` VARCHAR(16) NOT NULL COMMENT '密码盐值(16字节随机串)',
    `user_email` VARCHAR(32) NOT NULL COMMENT '用户邮箱',
    `user_role` VARCHAR(32) NOT NULL COMMENT '用户角色',
    `user_status` VARCHAR(1) NOT NULL COMMENT '用户状态',
    PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ========================================================
-- book (图书数据存储)
-- ========================================================
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
    `book_id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `book_name` VARCHAR(32) NOT NULL COMMENT '图书名称',
    `book_isbn` VARCHAR(13) NOT NULL COMMENT 'ISBN号',
    `book_press` VARCHAR(32) NOT NULL COMMENT '出版社',
    `book_author` VARCHAR(32) NOT NULL COMMENT '作者',
    `book_pagination` INT NOT NULL COMMENT '页数',
    `book_price` DOUBLE NOT NULL COMMENT '价格',
    `book_uploadtime` VARCHAR(32) NOT NULL COMMENT '上架时间',
    `book_status` VARCHAR(1) NOT NULL COMMENT '图书状态',
    `book_borrower` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '当前借阅人',
    `book_borrowtime` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '借阅时间',
    `book_returntime` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '应还时间',
    PRIMARY KEY (`book_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='图书数据表';

-- ========================================================
-- record (借阅记录存储)
-- ========================================================
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
    `record_id` VARCHAR(32) NOT NULL COMMENT '主键ID(记录编号)',
    `record_bookname` VARCHAR(32) NOT NULL COMMENT '图书名称',
    `record_bookisbn` VARCHAR(32) NOT NULL COMMENT '图书ISBN',
    `record_borrower` VARCHAR(32) NOT NULL COMMENT '借阅人',
    `record_borrowtime` VARCHAR(32) NOT NULL COMMENT '借阅时间',
    `record_remandtime` VARCHAR(32) NOT NULL COMMENT '归还时间',
    PRIMARY KEY (`record_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='借阅记录表';