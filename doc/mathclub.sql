/*
Navicat MySQL Data Transfer

Source Server         : mathclub
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mathclub

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-08-20 23:57:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `keypoint`
-- ----------------------------
DROP TABLE IF EXISTS `keypoint`;
CREATE TABLE `keypoint` (
  `keyId` int(11) NOT NULL AUTO_INCREMENT,
  `majorId` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`keyId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of keypoint
-- ----------------------------
INSERT INTO `keypoint` VALUES ('1', '5', '知识点1');
INSERT INTO `keypoint` VALUES ('2', '5', '李邱知识点');
INSERT INTO `keypoint` VALUES ('4', '9', '知识点测试');
INSERT INTO `keypoint` VALUES ('5', '9', '知识点测试');
INSERT INTO `keypoint` VALUES ('6', '9', '更新');

-- ----------------------------
-- Table structure for `login_log`
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `userId` int(11) NOT NULL,
  `loginAt` datetime NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  KEY `userId_index` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login_log
-- ----------------------------

-- ----------------------------
-- Table structure for `major`
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major` (
  `majorId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`majorId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES ('4', '高等数学');
INSERT INTO `major` VALUES ('5', '线性代数');
INSERT INTO `major` VALUES ('6', '学科测试');
INSERT INTO `major` VALUES ('7', '学科测试3');
INSERT INTO `major` VALUES ('8', '哈哈哈哈');

-- ----------------------------
-- Table structure for `session`
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session` (
  `id` varchar(33) NOT NULL,
  `userId` int(11) NOT NULL,
  `expireAt` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of session
-- ----------------------------
INSERT INTO `session` VALUES ('781100a1d63143d790d130d945e1049e', '8', '1503227120096');

-- ----------------------------
-- Table structure for `subject`
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `subjectId` int(11) NOT NULL AUTO_INCREMENT,
  `keyId` int(11) DEFAULT NULL,
  `majorId` int(11) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `pic` varchar(255) DEFAULT NULL,
  `apic` varchar(255) DEFAULT NULL,
  `hide` char(11) DEFAULT NULL,
  `answer` varchar(64) DEFAULT NULL,
  `answerNum` int(11) DEFAULT NULL,
  `hint` varchar(128) DEFAULT NULL,
  `author` varchar(128) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`subjectId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('8', null, '8', '更新', 'kkk', null, null, '哈哈哈', '0', null, null, '1,2,3', '2017-08-20 23:50:55');

-- ----------------------------
-- Table structure for `subject_like`
-- ----------------------------
DROP TABLE IF EXISTS `subject_like`;
CREATE TABLE `subject_like` (
  `userId` int(11) NOT NULL,
  `subjectId` int(11) DEFAULT NULL,
  `type` int(2) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject_like
-- ----------------------------
INSERT INTO `subject_like` VALUES ('1', '2', '1', '2017-08-10 23:01:00');
INSERT INTO `subject_like` VALUES ('2', '2', '1', '2017-08-10 23:05:24');
INSERT INTO `subject_like` VALUES ('3', '2', '1', '2017-08-10 23:15:23');

-- ----------------------------
-- Table structure for `upload_counter`
-- ----------------------------
DROP TABLE IF EXISTS `upload_counter`;
CREATE TABLE `upload_counter` (
  `uploadType` varchar(50) NOT NULL,
  `counter` int(11) NOT NULL,
  `descr` varchar(50) NOT NULL,
  PRIMARY KEY (`uploadType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of upload_counter
-- ----------------------------
INSERT INTO `upload_counter` VALUES ('subject', '2', 'shangc');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(255) DEFAULT NULL,
  `nickName` varchar(128) DEFAULT NULL,
  `headImgurl` varchar(255) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7', null, null, null, null, null);
INSERT INTO `user` VALUES ('8', 'ofkEcw5_4iciDfD6_u_EGuLSilF8', 'silence', 'http://wx.qlogo.cn/mmopen/zOP9dTzibRMggz7zdj5dSYtungKjDgLITI9w6hP8WpY7L6hEsJWv3BxFVticSqYIRSPsa2thTglMprC3uHP5OyVDxScJdP6NCv/0', '127.0.0.1', '2017-08-19 21:28:00');
