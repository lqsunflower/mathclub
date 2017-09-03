/*
Navicat MySQL Data Transfer

Source Server         : mathclub
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mathclub

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-03 23:08:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `subjectId` int(11) NOT NULL,
  `userName` varchar(64) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL,
  `isToSys` int(2) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

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
INSERT INTO `login_log` VALUES ('8', '2017-08-29 23:51:39', '127.0.0.1');

-- ----------------------------
-- Table structure for `major`
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major` (
  `majorId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`majorId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES ('4', '高等数学');
INSERT INTO `major` VALUES ('5', '线性代数');
INSERT INTO `major` VALUES ('6', '学科测试');
INSERT INTO `major` VALUES ('7', '学科测试3');
INSERT INTO `major` VALUES ('8', '哈哈哈哈');
INSERT INTO `major` VALUES ('9', 'test');

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
INSERT INTO `session` VALUES ('9b9ca4622a5a431288c19edec7cdfde7', '8', '1504029099072');

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('8', '3', '8', '更新', 'kkk', null, null, '哈哈哈', '0', null, null, '1,2,3', '2017-08-20 23:50:55');
INSERT INTO `subject` VALUES ('9', '3', '2', '学科', 's', 'd', 's', 's', '3', '3', null, null, null);
INSERT INTO `subject` VALUES ('10', '2', '3', '科目', '的', null, null, null, null, null, null, null, null);
INSERT INTO `subject` VALUES ('11', null, '99', '哈哈哈李邱', '000', null, null, 'xxxx', '0', null, null, null, '2017-08-31 23:14:29');
INSERT INTO `subject` VALUES ('12', null, '99', '哈哈李邱7777', '000', null, null, 'xxxx', null, null, null, null, '2017-08-31 23:34:29');

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
-- Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `majorId` int(11) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `subjectIds` varchar(128) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', '2', 'test1', '2,4,3', '2017-08-30 22:46:35', '2017-09-11 14:02:57');
INSERT INTO `test` VALUES ('2', '2', 'test333', '2,4,3', '2017-08-30 23:06:47', '2017-08-30 23:06:47');

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
INSERT INTO `upload_counter` VALUES ('image', '6', '图片文件');
INSERT INTO `upload_counter` VALUES ('video', '1', '视频文件');

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
