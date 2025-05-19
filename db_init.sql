-- 创建数据库
CREATE DATABASE IF NOT EXISTS `089_JDBC` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 使用数据库
USE `089_JDBC`;

-- 创建用户表
CREATE TABLE IF NOT EXISTS `user` (
  `uId` VARCHAR(20) PRIMARY KEY,
  `uName` VARCHAR(50) NOT NULL,
  `uPw` VARCHAR(50) NOT NULL,
  `uSchool` VARCHAR(100) NOT NULL,
  `uDepartment` VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建课程表
CREATE TABLE IF NOT EXISTS `course` (
  `cId` VARCHAR(20) PRIMARY KEY,
  `cName` VARCHAR(100) NOT NULL,
  `cType` ENUM('选修', '必修') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建选修课程表
CREATE TABLE IF NOT EXISTS `electiveSub` (
  `uId` VARCHAR(20) NOT NULL,
  `cId` VARCHAR(20) NOT NULL,
  `grade` DECIMAL(5,2),
  PRIMARY KEY (`uId`, `cId`),
  FOREIGN KEY (`uId`) REFERENCES `user`(`uId`),
  FOREIGN KEY (`cId`) REFERENCES `course`(`cId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 插入用户数据
INSERT IGNORE INTO `user` (`uId`, `uName`, `uPw`, `uSchool`, `uDepartment`) VALUES
('2021001', '张三', '123a', '计算机学院', '软件工程'),
('2021002', '李四', '123a', '电子信息学院', '电子工程'),
('2021003', '王五', '123a', '管理学院', '工商管理');

-- 插入课程数据
INSERT IGNORE INTO `course` (`cId`, `cName`, `cType`) VALUES
('C001', 'Java程序设计', '必修'),
('C002', 'Web开发技术', '必修'),
('C003', '数据结构', '必修'),
('C004', '软件工程', '必修'),
('C005', 'JavaEE实用基础', '选修'),
('C006', 'C++程序设计', '选修'),
('C007', '软件课程设计I', '选修'),
('C008', '数据库原理', '必修'),
('C009', '操作系统', '必修'),
('C010', '计算机网络', '必修');

-- 插入选修课程数据
INSERT IGNORE INTO `electiveSub` (`uId`, `cId`, `grade`) VALUES
('2021001', 'C001', 85.5),
('2021001', 'C002', 90.0),
('2021001', 'C005', 100.0),
('2021001', 'C006', 90.0),
('2021001', 'C007', 90.0),
('2021002', 'C002', 78.0),
('2021002', 'C008', 85.0),
('2021002', 'C009', 88.0),
('2021003', 'C005', 95.0),
('2021003', 'C008', 92.0),
('2021003', 'C010', 86.5); 