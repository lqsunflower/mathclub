/*
MySQL Data Transfer
Source Host: localhost
Source Database: mathclub
Target Host: localhost
Target Database: mathclub
Date: 2017/10/17 11:16:10
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(150) NOT NULL,
  `password` varchar(150) NOT NULL,
  `salt` varchar(150) NOT NULL,
  `status` int(11) NOT NULL,
  `createAt` datetime NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `account` VALUES ('1000', 'lyj', 'cd104216c2036d72ca817d011d3fbad75ad7d487101d0a126adc6cadb92110c0', 'xSyEf-wcLrQyirCOQ2sbaabziVJKyxLR', '0', '2017-10-08 12:05:36', '123');
INSERT INTO `account` VALUES ('1001', 'mathclub', '0a586854d7a35a264878d7040fafe8284f182e1dc3ecb76fb8a6cd044717de44', 'xSyEf-wcLrQyirCOQ2sbaabziVJKyxLR', '0', '2017-10-17 12:57:41', '12322');
