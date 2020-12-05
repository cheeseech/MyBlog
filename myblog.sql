/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : myblog

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-12-05 22:15:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL,
  `md_content` text,
  `html_content` text,
  `summary` text,
  `type_id` int(11) DEFAULT NULL,
  `cate_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `publish_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `recommend` tinyint(1) DEFAULT NULL COMMENT '是否推荐 1是 0否',
  `article_state` tinyint(1) DEFAULT NULL,
  `views` int(11) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `comments` int(11) DEFAULT NULL,
  `first_picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`article_id`),
  KEY `FK_article_types` (`type_id`),
  KEY `FK_article_user` (`user_id`),
  KEY `FK_article_cate` (`cate_id`),
  CONSTRAINT `FK_article_cate` FOREIGN KEY (`cate_id`) REFERENCES `category` (`cate_id`),
  CONSTRAINT `FK_article_types` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`),
  CONSTRAINT `FK_article_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='??????????Ϣ';

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', '失败要趁早', '1', '1', '老太太说：“我就特别看不起你们这帮年轻人，二三十岁就叨逼叨说平平淡淡才是真。\r\n          你们配吗？我上山下乡，知青当过，饥荒捱过，这你们没办法经历。\r\n          我的平平淡淡是苦出来的，你们的平平淡淡是懒惰...', '1', '1', '1', '2020-07-31 20:59:59', '2020-10-07 17:52:56', '1', '1', '91', '81', '90', '1');
INSERT INTO `article` VALUES ('2', 'Ajax上传图片以及上传之前先预览', '1', '1', '年初的时候，写过两篇博客介绍在Spring Boot中如何使用WebSocket发送消息【在Spri', '2', '3', '1', '2020-07-29 06:02:57', '2020-10-27 16:47:53', '1', '0', '90', '85', '95', '1');
INSERT INTO `article` VALUES ('3', '一个简单的案例带你入门Dubbo分布', '1', '1', '老太太说：“我就特别看不起你们这帮年轻人，二三十岁就叨逼叨说平平淡淡才是真。\r\n          你们配吗？我上山下乡，知青当过，饥荒捱过，这你们没办法经历。\r\n相信有很多小伙伴都知道，dubbo是一个分布式、高性能、透明化的RPC服务框架，提供服务自动注册、自', '3', '4', '1', '2018-05-30 07:58:59', '2020-10-28 13:49:52', '1', '-1', '71', '79', '83', '1');
INSERT INTO `article` VALUES ('4', '一点点Github上的学习资料', '1', '1', '老太太说：“我就特别看不起你们这帮年轻人，二三十岁就叨逼叨说平平淡淡才是真。\r\n          你们配吗？我上山下乡，知青当过，饥荒捱过，这你们没办法经历。\r\n年初的时候，写过两篇博客介绍在Spring Boot中如何使用WebSocket发送消息【在Spri', '3', '1', '1', '2018-04-30 02:59:00', '2020-10-29 15:06:06', '1', '1', '95', '93', '90', '1');
INSERT INTO `article` VALUES ('5', '失败要趁早1', '1', '1', '老太太说：“我就特别看不起你们这帮年轻人，二三十岁就叨逼叨说平平淡淡才是真。\r\n          你们配吗？我上山下乡，知青当过，饥荒捱过，这你们没办法经历。\r\n          我的平平淡淡是苦出来的，你们的平平淡淡是懒惰...', '1', '1', '1', '2020-07-30 18:57:52', '2020-10-07 06:04:04', '1', '-1', '105', '64', '94', '1');
INSERT INTO `article` VALUES ('6', '失败要趁早2', '1', '1', '老太太说：“我就特别看不起你们这帮年轻人，二三十岁就叨逼叨说平平淡淡才是真。\r\n          你们配吗？我上山下乡，知青当过，饥荒捱过，这你们没办法经历。\r\n          我的平平淡淡是苦出来的，你们的平平淡淡是懒惰...', '1', '1', '1', '2020-07-30 10:50:48', '2020-10-07 19:04:55', '1', '1', '69', '77', '96', '1');
INSERT INTO `article` VALUES ('7', '失败要趁早3', '1', '1', '老太太说：“我就特别看不起你们这帮年轻人，二三十岁就叨逼叨说平平淡淡才是真。\r\n          你们配吗？我上山下乡，知青当过，饥荒捱过，这你们没办法经历。\r\n          我的平平淡淡是苦出来的，你们的平平淡淡是懒惰...', '1', '1', '1', '2020-07-30 17:05:04', '2020-10-07 06:06:06', '1', '0', '88', '90', '93', '1');

-- ----------------------------
-- Table structure for article_tags
-- ----------------------------
DROP TABLE IF EXISTS `article_tags`;
CREATE TABLE `article_tags` (
  `at_id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`at_id`),
  KEY `FK_at_tags` (`tag_id`),
  KEY `FK_at_article` (`article_id`),
  CONSTRAINT `FK_at_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
  CONSTRAINT `FK_at_tags` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='???????±?ǩ';

-- ----------------------------
-- Records of article_tags
-- ----------------------------
INSERT INTO `article_tags` VALUES ('2', '1', '1');
INSERT INTO `article_tags` VALUES ('3', '1', '4');
INSERT INTO `article_tags` VALUES ('4', '1', '5');
INSERT INTO `article_tags` VALUES ('5', '1', '6');
INSERT INTO `article_tags` VALUES ('6', '2', '1');
INSERT INTO `article_tags` VALUES ('7', '1', '7');
INSERT INTO `article_tags` VALUES ('8', '2', '7');
INSERT INTO `article_tags` VALUES ('9', '2', '4');
INSERT INTO `article_tags` VALUES ('10', '3', '7');
INSERT INTO `article_tags` VALUES ('11', '3', '4');
INSERT INTO `article_tags` VALUES ('12', '4', '1');
INSERT INTO `article_tags` VALUES ('13', '4', '7');
INSERT INTO `article_tags` VALUES ('14', '4', '6');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cate_id` int(11) NOT NULL AUTO_INCREMENT,
  `cate_name` varchar(64) DEFAULT NULL,
  `cate_summary` varchar(255) DEFAULT NULL COMMENT '专栏概述',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`cate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='????ר????Ϣ';

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', 'Java', '生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念', '2020-07-30 00:00:00');
INSERT INTO `category` VALUES ('3', 'C', '与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念', '2020-08-03 00:00:00');
INSERT INTO `category` VALUES ('4', 'C++', '现实一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念', '2020-08-05 00:00:00');
INSERT INTO `category` VALUES ('8', '更新1', '更新内容', '2020-10-25 01:05:59');
INSERT INTO `category` VALUES ('11', '哈哈', '哈哈哈', '2020-12-05 13:21:56');

-- ----------------------------
-- Table structure for days_data
-- ----------------------------
DROP TABLE IF EXISTS `days_data`;
CREATE TABLE `days_data` (
  `days_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `days` date NOT NULL COMMENT '日期',
  `views` int(10) DEFAULT '0' COMMENT '浏览量',
  `likes` int(10) DEFAULT '0' COMMENT '点赞数',
  `comments` int(10) DEFAULT '0' COMMENT '评论数',
  `article_id` int(11) NOT NULL COMMENT '文章数',
  PRIMARY KEY (`days_id`),
  KEY `FK_article_days` (`article_id`),
  CONSTRAINT `FK_article_days` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of days_data
-- ----------------------------
INSERT INTO `days_data` VALUES ('1', '2020-11-18', '10', '3', '2', '1');
INSERT INTO `days_data` VALUES ('2', '2020-11-18', '9', '2', '1', '2');
INSERT INTO `days_data` VALUES ('3', '2020-11-18', '7', '2', '2', '3');
INSERT INTO `days_data` VALUES ('4', '2020-11-18', '8', '2', '2', '4');
INSERT INTO `days_data` VALUES ('5', '2020-11-18', '6', '2', '2', '5');
INSERT INTO `days_data` VALUES ('6', '2020-11-18', '5', '1', '1', '6');
INSERT INTO `days_data` VALUES ('7', '2020-11-18', '4', '1', '1', '7');
INSERT INTO `days_data` VALUES ('8', '2020-11-17', '7', '8', '5', '1');
INSERT INTO `days_data` VALUES ('9', '2020-11-17', '4', '4', '3', '2');
INSERT INTO `days_data` VALUES ('10', '2020-11-17', '8', '6', '4', '3');
INSERT INTO `days_data` VALUES ('11', '2020-11-17', '3', '9', '8', '4');
INSERT INTO `days_data` VALUES ('12', '2020-11-17', '2', '3', '7', '5');
INSERT INTO `days_data` VALUES ('13', '2020-11-17', '2', '2', '4', '6');
INSERT INTO `days_data` VALUES ('14', '2020-11-17', '6', '9', '6', '7');
INSERT INTO `days_data` VALUES ('15', '2020-11-16', '9', '0', '2', '1');
INSERT INTO `days_data` VALUES ('16', '2020-11-16', '8', '9', '4', '2');
INSERT INTO `days_data` VALUES ('17', '2020-11-16', '2', '7', '3', '3');
INSERT INTO `days_data` VALUES ('18', '2020-11-16', '0', '4', '5', '4');
INSERT INTO `days_data` VALUES ('19', '2020-11-16', '5', '3', '6', '5');
INSERT INTO `days_data` VALUES ('20', '2020-11-16', '5', '0', '9', '6');
INSERT INTO `days_data` VALUES ('21', '2020-11-16', '0', '5', '4', '7');
INSERT INTO `days_data` VALUES ('22', '2020-11-15', '7', '7', '7', '1');
INSERT INTO `days_data` VALUES ('23', '2020-11-15', '8', '0', '2', '2');
INSERT INTO `days_data` VALUES ('24', '2020-11-15', '2', '7', '0', '3');
INSERT INTO `days_data` VALUES ('25', '2020-11-15', '5', '6', '3', '4');
INSERT INTO `days_data` VALUES ('26', '2020-11-15', '8', '5', '6', '5');
INSERT INTO `days_data` VALUES ('27', '2020-11-15', '4', '4', '9', '6');
INSERT INTO `days_data` VALUES ('28', '2020-11-15', '6', '1', '0', '7');
INSERT INTO `days_data` VALUES ('29', '2020-11-14', '6', '8', '3', '1');
INSERT INTO `days_data` VALUES ('30', '2020-11-14', '5', '4', '8', '2');
INSERT INTO `days_data` VALUES ('31', '2020-11-14', '6', '9', '8', '3');
INSERT INTO `days_data` VALUES ('32', '2020-11-14', '2', '8', '5', '4');
INSERT INTO `days_data` VALUES ('33', '2020-11-14', '1', '2', '7', '5');
INSERT INTO `days_data` VALUES ('34', '2020-11-14', '8', '7', '1', '6');
INSERT INTO `days_data` VALUES ('35', '2020-11-14', '8', '1', '2', '7');
INSERT INTO `days_data` VALUES ('36', '2020-11-13', '9', '9', '5', '1');
INSERT INTO `days_data` VALUES ('37', '2020-11-13', '0', '9', '5', '2');
INSERT INTO `days_data` VALUES ('38', '2020-11-13', '4', '6', '7', '3');
INSERT INTO `days_data` VALUES ('39', '2020-11-13', '5', '3', '0', '4');
INSERT INTO `days_data` VALUES ('40', '2020-11-13', '6', '1', '1', '5');
INSERT INTO `days_data` VALUES ('41', '2020-11-13', '2', '4', '0', '6');
INSERT INTO `days_data` VALUES ('42', '2020-11-13', '4', '5', '4', '7');
INSERT INTO `days_data` VALUES ('43', '2020-11-12', '3', '8', '4', '1');
INSERT INTO `days_data` VALUES ('44', '2020-11-12', '7', '7', '3', '2');
INSERT INTO `days_data` VALUES ('45', '2020-11-12', '3', '1', '0', '3');
INSERT INTO `days_data` VALUES ('46', '2020-11-12', '7', '6', '7', '4');
INSERT INTO `days_data` VALUES ('47', '2020-11-12', '9', '0', '5', '5');
INSERT INTO `days_data` VALUES ('48', '2020-11-12', '0', '3', '8', '6');
INSERT INTO `days_data` VALUES ('49', '2020-11-12', '6', '6', '6', '7');
INSERT INTO `days_data` VALUES ('50', '2020-11-11', '4', '9', '1', '1');
INSERT INTO `days_data` VALUES ('51', '2020-11-11', '1', '8', '9', '2');
INSERT INTO `days_data` VALUES ('52', '2020-11-11', '5', '5', '0', '3');
INSERT INTO `days_data` VALUES ('53', '2020-11-11', '1', '4', '6', '4');
INSERT INTO `days_data` VALUES ('54', '2020-11-11', '8', '6', '6', '5');
INSERT INTO `days_data` VALUES ('55', '2020-11-11', '2', '1', '8', '6');
INSERT INTO `days_data` VALUES ('56', '2020-11-11', '9', '0', '3', '7');
INSERT INTO `days_data` VALUES ('57', '2020-11-10', '3', '2', '5', '1');
INSERT INTO `days_data` VALUES ('58', '2020-11-10', '3', '2', '4', '2');
INSERT INTO `days_data` VALUES ('59', '2020-11-10', '5', '1', '7', '3');
INSERT INTO `days_data` VALUES ('60', '2020-11-10', '7', '4', '7', '4');
INSERT INTO `days_data` VALUES ('61', '2020-11-10', '6', '9', '7', '5');
INSERT INTO `days_data` VALUES ('62', '2020-11-10', '3', '9', '7', '6');
INSERT INTO `days_data` VALUES ('63', '2020-11-10', '6', '1', '6', '7');
INSERT INTO `days_data` VALUES ('64', '2020-11-09', '7', '5', '8', '1');
INSERT INTO `days_data` VALUES ('65', '2020-11-09', '5', '2', '8', '2');
INSERT INTO `days_data` VALUES ('66', '2020-11-09', '0', '0', '6', '3');
INSERT INTO `days_data` VALUES ('67', '2020-11-09', '8', '5', '9', '4');
INSERT INTO `days_data` VALUES ('68', '2020-11-09', '5', '0', '0', '5');
INSERT INTO `days_data` VALUES ('69', '2020-11-09', '7', '8', '6', '6');
INSERT INTO `days_data` VALUES ('70', '2020-11-09', '2', '9', '8', '7');
INSERT INTO `days_data` VALUES ('71', '2020-11-08', '0', '1', '9', '1');
INSERT INTO `days_data` VALUES ('72', '2020-11-08', '5', '3', '8', '2');
INSERT INTO `days_data` VALUES ('73', '2020-11-08', '5', '1', '2', '3');
INSERT INTO `days_data` VALUES ('74', '2020-11-08', '6', '6', '9', '4');
INSERT INTO `days_data` VALUES ('75', '2020-11-08', '8', '3', '7', '5');
INSERT INTO `days_data` VALUES ('76', '2020-11-08', '2', '7', '2', '6');
INSERT INTO `days_data` VALUES ('77', '2020-11-08', '5', '8', '7', '7');
INSERT INTO `days_data` VALUES ('78', '2020-11-07', '6', '1', '4', '1');
INSERT INTO `days_data` VALUES ('79', '2020-11-07', '8', '8', '9', '2');
INSERT INTO `days_data` VALUES ('80', '2020-11-07', '4', '3', '3', '3');
INSERT INTO `days_data` VALUES ('81', '2020-11-07', '9', '4', '0', '4');
INSERT INTO `days_data` VALUES ('82', '2020-11-07', '0', '2', '9', '5');
INSERT INTO `days_data` VALUES ('83', '2020-11-07', '6', '0', '8', '6');
INSERT INTO `days_data` VALUES ('84', '2020-11-07', '6', '8', '1', '7');
INSERT INTO `days_data` VALUES ('85', '2020-11-06', '0', '0', '5', '1');
INSERT INTO `days_data` VALUES ('86', '2020-11-06', '9', '9', '6', '2');
INSERT INTO `days_data` VALUES ('87', '2020-11-06', '6', '4', '8', '3');
INSERT INTO `days_data` VALUES ('88', '2020-11-06', '8', '9', '1', '4');
INSERT INTO `days_data` VALUES ('89', '2020-11-06', '8', '7', '9', '5');
INSERT INTO `days_data` VALUES ('90', '2020-11-06', '6', '2', '8', '6');
INSERT INTO `days_data` VALUES ('91', '2020-11-06', '1', '9', '7', '7');
INSERT INTO `days_data` VALUES ('92', '2020-11-05', '8', '8', '9', '1');
INSERT INTO `days_data` VALUES ('93', '2020-11-05', '2', '1', '4', '2');
INSERT INTO `days_data` VALUES ('94', '2020-11-05', '5', '5', '9', '3');
INSERT INTO `days_data` VALUES ('95', '2020-11-05', '0', '6', '4', '4');
INSERT INTO `days_data` VALUES ('96', '2020-11-05', '8', '5', '1', '5');
INSERT INTO `days_data` VALUES ('97', '2020-11-05', '9', '6', '7', '6');
INSERT INTO `days_data` VALUES ('98', '2020-11-05', '0', '2', '3', '7');
INSERT INTO `days_data` VALUES ('99', '2020-11-04', '0', '3', '1', '1');
INSERT INTO `days_data` VALUES ('100', '2020-11-04', '2', '3', '5', '2');
INSERT INTO `days_data` VALUES ('101', '2020-11-04', '3', '6', '9', '3');
INSERT INTO `days_data` VALUES ('102', '2020-11-04', '8', '1', '8', '4');
INSERT INTO `days_data` VALUES ('103', '2020-11-04', '9', '5', '3', '5');
INSERT INTO `days_data` VALUES ('104', '2020-11-04', '1', '8', '6', '6');
INSERT INTO `days_data` VALUES ('105', '2020-11-04', '8', '4', '8', '7');
INSERT INTO `days_data` VALUES ('106', '2020-11-03', '6', '1', '6', '1');
INSERT INTO `days_data` VALUES ('107', '2020-11-03', '1', '3', '9', '2');
INSERT INTO `days_data` VALUES ('108', '2020-11-03', '5', '0', '4', '3');
INSERT INTO `days_data` VALUES ('109', '2020-11-03', '5', '7', '4', '4');
INSERT INTO `days_data` VALUES ('110', '2020-11-03', '4', '0', '1', '5');
INSERT INTO `days_data` VALUES ('111', '2020-11-03', '4', '5', '3', '6');
INSERT INTO `days_data` VALUES ('112', '2020-11-03', '3', '3', '9', '7');
INSERT INTO `days_data` VALUES ('113', '2020-11-02', '6', '6', '8', '1');
INSERT INTO `days_data` VALUES ('114', '2020-11-02', '6', '6', '3', '2');
INSERT INTO `days_data` VALUES ('115', '2020-11-02', '1', '8', '8', '3');
INSERT INTO `days_data` VALUES ('116', '2020-11-02', '9', '3', '3', '4');
INSERT INTO `days_data` VALUES ('117', '2020-11-02', '9', '9', '9', '5');
INSERT INTO `days_data` VALUES ('118', '2020-11-02', '3', '7', '2', '6');
INSERT INTO `days_data` VALUES ('119', '2020-11-02', '9', '9', '9', '7');
INSERT INTO `days_data` VALUES ('120', '2020-11-01', '0', '2', '6', '1');
INSERT INTO `days_data` VALUES ('121', '2020-11-01', '7', '5', '4', '2');
INSERT INTO `days_data` VALUES ('122', '2020-11-01', '0', '8', '3', '3');
INSERT INTO `days_data` VALUES ('123', '2020-11-01', '4', '6', '9', '4');
INSERT INTO `days_data` VALUES ('124', '2020-11-01', '3', '2', '8', '5');
INSERT INTO `days_data` VALUES ('125', '2020-11-01', '0', '3', '7', '6');
INSERT INTO `days_data` VALUES ('126', '2020-11-01', '5', '9', '9', '7');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(10) DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin');
INSERT INTO `role` VALUES ('2', 'user');

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色流水号',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_user
-- ----------------------------
INSERT INTO `role_user` VALUES ('1', '1', '1');
INSERT INTO `role_user` VALUES ('2', '2', '1');

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(64) NOT NULL,
  `tag_type` varchar(64) NOT NULL COMMENT '标签类型',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='???ű?ǩ??Ϣ';

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES ('1', 'Java', 'warning');
INSERT INTO `tags` VALUES ('4', 'demoDa', 'danger');
INSERT INTO `tags` VALUES ('5', 'HTML', 'info');
INSERT INTO `tags` VALUES ('6', 'CSS', 'primary ');
INSERT INTO `tags` VALUES ('7', 'JavaScript', 'success');
INSERT INTO `tags` VALUES ('8', '测试', 'primary');
INSERT INTO `tags` VALUES ('9', 'Vue', 'success');
INSERT INTO `tags` VALUES ('10', 'mybatis', 'warning');
INSERT INTO `tags` VALUES ('12', '更新', 'warning');

-- ----------------------------
-- Table structure for types
-- ----------------------------
DROP TABLE IF EXISTS `types`;
CREATE TABLE `types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `type_name` varchar(64) NOT NULL COMMENT '类型名',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='???????ͣ?ԭ????ת?ء????';

-- ----------------------------
-- Records of types
-- ----------------------------
INSERT INTO `types` VALUES ('1', '原 创');
INSERT INTO `types` VALUES ('2', '转 载');
INSERT INTO `types` VALUES ('3', '翻 译');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_face` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '用户状态：1激活 0禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='?洢?û???Ϣ';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '芝士', 'admin', 'admin', '1355125277@qq.com', '1', '2020-07-30 01:01:59', '2020-09-10 20:59:58');
INSERT INTO `user` VALUES ('2', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '水电收费站', 'admin1', 'admin', '1355125277@qq.com', '1', '2020-07-30 01:01:59', '2020-10-18 08:15:29');
INSERT INTO `user` VALUES ('3', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '下城许寮', 'admin2', 'admin', '1355125277@qq.com', '0', '2020-07-30 01:01:59', '2020-10-18 08:15:23');
INSERT INTO `user` VALUES ('4', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '合同法', 'admin3', 'admin', '1355125277@qq.com', '1', '2020-07-30 01:01:59', '2020-09-10 20:59:58');
INSERT INTO `user` VALUES ('5', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '就给你', 'admin4', 'admin', '1355125277@qq.com', '1', '2020-07-30 01:01:59', '2020-10-21 07:09:17');
INSERT INTO `user` VALUES ('7', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '京东方', 'admin6', 'admin', '1355125277@qq.com', '1', '2020-07-30 01:01:59', '2020-09-10 20:59:58');
INSERT INTO `user` VALUES ('14', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '荒野编辑', 'admin8', 'admin', '1355125277@qq.com', '1', '2020-10-21 08:18:41', '2020-10-22 05:43:08');
INSERT INTO `user` VALUES ('15', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', '新增用户', 'admin9', 'admin', '1355125277@qq.com', '1', '2020-10-22 05:43:45', '2020-10-22 05:43:45');
