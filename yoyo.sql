/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 5.5.50 : Database - yoyo
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`yoyo` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `yoyo`;

/*Table structure for table `admins` */

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `username` varchar(255) DEFAULT NULL COMMENT '管理员名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `admins` */

insert  into `admins`(`id`,`username`,`password`) values 

(1,'admin','HAMVRZRssPCADKqGjGWJtQ==');

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面',
  `image1` varchar(255) DEFAULT NULL COMMENT '细节图片1',
  `image2` varchar(255) DEFAULT NULL COMMENT '细节图片2',
  `price` int(11) DEFAULT NULL COMMENT '价格',
  `intro` varchar(255) DEFAULT NULL COMMENT '介绍',
  `stock` int(11) DEFAULT '0' COMMENT '库存',
  `type_id` int(11) DEFAULT NULL COMMENT '分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Data for the table `goods` */

insert  into `goods`(`id`,`name`,`cover`,`image1`,`image2`,`price`,`intro`,`stock`,`type_id`) values 

(15,'DANISA/皇冠曲奇饼干','picture/21.jpg','picture/20.jpg','picture/19.jpg',98,'百草味-冻干榴莲干30g/袋休闲零食特产水果干金枕头泰国风味进口',100,1),

(16,'冻干榴莲干','picture/6.jpg','picture/5.jpg','picture/4.jpg',13,'百草味-冻干榴莲干30g/袋休闲零食特产水果干金枕头泰国风味进口',50,5),

(17,'卤味零食大礼包','picture/8.jpg','picture/7.jpg','picture/9.jpg',49,'包邮三只松鼠零食大礼包505g卤味鸭脖整箱鸭肉小吃充饥夜宵送女友',50,5),

(18,'猪肉脯','picture/2.jpg','picture/3.jpg','picture/1.jpg',28,'良品铺子猪肉脯100g靖江特产猪肉干网红小吃办公室休闲零食猪肉铺',50,5),

(19,'山核桃仁','picture/10.jpg','picture/11.jpg','picture/12.jpg',149,'包邮三只松鼠山核桃仁500g30天装坚果量贩装每日坚果送礼孕妇干果',50,5),

(20,'熟制板栗仁','picture/15.jpg','picture/14.jpg','picture/13.jpg',12,'百草味 熟制板栗仁80g*2坚果炒货零食特产甘栗仁新鲜即食干果栗子',50,5),

(21,'什锦蔬菜脆','picture/16.jpg','picture/18.jpg','picture/17.jpg',12,'百草味什锦蔬菜脆108g果蔬干秋葵脆片综合水果零食混合装脱水即食',50,5),

(24,'农夫山泉','picture/23.jpg','picture/22.jpg','picture/24.jpg',16,'农夫山泉饮用天然水550ml*12瓶/组春游踏春出行露营居家常备',50,5),

(25,'百事可乐','picture/25.jpg','picture/26.jpg','picture/27.jpg',45,'百事可乐原味碳酸汽水330mL*24罐经典罐饮料饮品整箱装 宅家囤货',50,5),

(26,'儿童磨牙棒饼干','picture/28.jpg','picture/29.jpg','picture/30.jpg',38,'韩国进口艾唯倪宝宝零食无添加蔗糖盐原味米饼30g儿童磨牙棒饼干',50,1),

(27,'奶酪威化饼干','picture/31.jpg','picture/32.jpg','picture/33.jpg',10,'【进口】印尼丽芝士nabati纳宝帝奶酪威化饼干290g网红休闲零食品',100,1),

(28,'欧德堡全脂纯牛奶','picture/36.png','picture/35.jpg','picture/34.jpg',60,'【进口】德国欧德堡全脂纯牛奶200ml*16盒早餐牛奶营养牛奶整箱',50,1),

(29,'德亚儿童牛奶','picture/37.png','picture/38.jpg','picture/39.jpg',30,'德国进口牛奶 德亚儿童宝宝婴儿婴幼儿牛奶调制乳 200ml*3 汪汪队',100,1),

(30,'旧街场白咖啡','picture/40.jpg','picture/41.jpg','picture/42.jpg',62,'【进口】马来西亚旧街场白咖啡榛果味20条盒装3合1榛果浓香',50,1),

(31,'农心辣白菜小碗面','picture/43.jpg','picture/44.jpg','picture/45.jpg',7,'韩国进口农心辣白菜小碗面86g方便面泡菜拉面泡面杯面',50,1),

(32,'纯可可黑巧克力','picture/46.jpg','picture/47.jpg','picture/48.jpg',20,'诺梵88%纯可可黑巧克力礼盒装休闲零食520礼物礼品送女友130g',50,1),

(33,'川娃子炭烧烧椒酱辣椒酱','picture/49.png','picture/50.jpg','picture/51.jpg',14,'川娃子炭烧烧椒酱辣椒酱230g农家手工虎皮青辣椒剁椒酱拌面下饭菜',50,4),

(34,'乌江涪陵榨菜','picture/52.jpg','picture/53.jpg','picture/54.jpg',14,'乌江涪陵清爽榨菜鲜脆菜丝70g*5酱菜泡菜下饭菜咸菜小菜早餐配粥',50,4),

(35,'老干妈豆豉油辣椒','picture/55.jpg','picture/56.jpg','picture/57.jpg',13,'陶华碧老干妈风味豆豉油辣椒280g辣椒酱辣酱调味酱下饭菜佐餐酱',50,4),

(36,'古方红糖','picture/58.jpg','picture/59.jpg','picture/60.jpg',39,'古方红糖180g/罐古法手工老红糖块经期产妇月子土红糖姨妈黑糖',50,4),

(37,'自嗨锅自热米饭','picture/61.jpg','picture/62.jpg','picture/63.jpg',60,'自嗨锅自热米饭大份量方便速食4桶广式香肠菌菇牛肉煲仔饭多口味',50,4),

(38,'双汇王中王火腿肠','picture/64.png','picture/65.jpg','picture/66.jpg',11,'双汇王中王火腿肠30g*8支香肠肉类休闲零食速食配泡面网红零食',50,4),

(39,'午餐肉罐头','picture/67.jpg','picture/68.jpg','picture/69.jpg',17,'【上海梅林】午餐肉罐头198g方便速食螺蛳粉火锅泡面搭档即食',50,4),

(40,'盖亚农场云南紫米','picture/70.jpg','picture/72.jpg','picture/71.jpg',30,'盖亚农场云南紫米1kg粗粮饭五谷杂粮墨江紫糯米黑米黑糯米饭团',50,4),

(41,'舒肤佳香皂','picture/73.jpg','picture/74.jpg','picture/75.jpg',14,'舒肤佳沐浴香皂纯白清香清香型男女持久留香抑菌家庭装115g*3肥皂',50,3),

(42,'李施德漱口水','picture/76.jpg','picture/77.jpg','picture/78.jpg',21,'李施德林零度漱口水进口无酒精除口臭清新口气便携去牙渍口腔清洁',50,3),

(43,'云南白药牙膏','picture/79.jpg','picture/80.png','picture/81.png',33,'云南白药牙膏薄荷清爽210g去牙渍去口臭口气清新家用家庭正品',50,3),

(44,'舒肤佳沐浴露','picture/82.jpg','picture/83.png','picture/84.jpg',44,'舒肤佳沐浴露官方正品柠檬700ml+纯白700ml男女持久留香家庭装',50,3),

(45,'高露洁牙刷','picture/85.jpg','picture/86.jpg','picture/87.jpg',12,'高露洁纤柔含炭超细软毛牙刷5支深入清洁去牙渍家庭套装家用组合',100,3),

(46,'潘婷氨基酸膜护发素','picture/88.jpg','picture/89.jpg','picture/90.jpg',19,'潘婷3分钟三分钟奇迹氨基酸发膜护发素70ml改善毛躁护色修护烫染',50,3),

(47,'欧莱雅男士洗面奶','picture/91.jpg','picture/92.jpg','picture/93.jpg',36,'欧莱雅男士洗面奶控油炭爽双效去黑头清洁收缩毛孔洁面膏100ml',50,3),

(48,'凡士林身体乳','picture/94.jpg','picture/95.jpg','picture/96.jpg',49,'凡士林亮采修护夏维他亮肤提亮身体乳400ML烟酰胺匀均肤色防晒',50,3),

(49,'汰渍洗衣粉','picture/97.jpg','picture/98.jpg','picture/99.jpg',39,'【薇娅推荐】汰渍洗衣粉10斤装净白去渍柠檬清袋装洗衣护理',50,2),

(50,'雷达杀虫气雾剂','picture/100.jpg','picture/101.jpg','picture/102.jpg',34,'雷达杀虫气雾剂无香550ml*2灭蟑螂杀苍蝇喷雾剂 杀虫剂家用驱蚊',50,2),

(51,'榄菊蚊香','picture/103.jpg','picture/104.jpg','picture/105.jpg',9,'榄菊蚊香艾草40单圈送支架家用室内驱蚊灭蚊艾叶盘香婴儿孕妇适用',50,2),

(52,'奥妙洗衣液','picture/106.jpg','picture/107.jpg','picture/108.jpg',37,'奥妙 除菌除螨洗衣液3KG 酵素隔断技术 长效抑菌 除菌除螨祛渍',50,2),

(53,'超能清洁肥皂','picture/109.jpg','picture/110.jpg','picture/111.jpg',9,'超能内衣专用皂202g*2清洗血渍去异味抑菌内裤清洁肥皂男女通用好',50,2),

(54,'Dettol/滴露消毒液','picture/112.jpg','picture/113.jpg','picture/114.jpg',54,'【薇娅推荐】Dettol/滴露皮肤衣物衣服家居消毒液750ml 有效杀菌',50,2),

(55,'金纺护理剂','picture/115.jpg','picture/116.jpg','picture/117.jpg',47,'金纺花漾香氛护理剂普罗旺斯薰衣草 500ML 护衣留香凝露',50,2),

(56,'狮王酵素除渍','picture/118.jpg','picture/119.jpg','picture/120.jpg',39,'日本进口狮王TOP高效洁白衣领净250ml酵素除渍领口袖口去污洗衣液',50,2);

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` int(11) DEFAULT NULL COMMENT '购买时价格',
  `amount` int(11) DEFAULT NULL COMMENT '购买数量',
  `order_id` int(11) DEFAULT NULL COMMENT '订单id',
  `good_id` int(11) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

/*Data for the table `items` */

insert  into `items`(`id`,`price`,`amount`,`order_id`,`good_id`) values 

(1,299,1,1,6),

(2,269,1,1,5),

(3,38,1,1,11),

(4,299,1,2,7),

(5,299,1,3,7),

(6,98,1,4,17),

(7,54,1,4,18),

(8,12,1,5,21),

(9,28,1,5,18),

(10,45,1,5,25),

(11,12,1,6,20),

(12,12,1,7,20),

(13,49,1,8,17),

(14,45,1,9,25),

(15,45,1,10,25),

(16,45,1,11,25),

(17,20,1,12,32),

(18,45,1,12,25),

(19,49,1,12,17),

(20,45,1,13,25),

(21,28,1,13,18),

(22,60,1,14,28),

(23,60,1,15,28),

(24,60,1,16,28),

(25,28,1,17,18),

(26,45,3,18,25),

(27,60,8,19,28),

(28,60,1,20,28),

(29,28,1,20,18),

(30,60,1,21,28),

(31,49,1,21,48),

(32,28,3,22,18),

(33,45,3,22,25),

(34,60,1,23,28),

(35,19,1,23,46);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` int(11) DEFAULT NULL COMMENT '总价',
  `amount` int(11) DEFAULT NULL COMMENT '商品总数',
  `status` tinyint(4) DEFAULT '1' COMMENT '订单状态(1未付款/2已付款/3已发货/4已完成)',
  `paytype` tinyint(4) DEFAULT '0' COMMENT '支付方式 (1微信/2支付宝/3货到付款)',
  `name` varchar(255) DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) DEFAULT NULL COMMENT '收货电话',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `systime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
  `user_id` int(11) DEFAULT NULL COMMENT '下单用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`id`,`total`,`amount`,`status`,`paytype`,`name`,`phone`,`address`,`systime`,`user_id`) values 

(15,60,1,3,3,'刘','12323123123','4#223宿舍','2021-06-01 15:37:35',1),

(17,28,1,2,1,'刘','12323123123','4#223宿舍','2021-06-01 18:35:38',1),

(18,135,3,1,NULL,NULL,NULL,NULL,'2021-06-13 15:30:43',1),

(19,480,8,1,NULL,NULL,NULL,NULL,'2021-06-13 16:42:18',1),

(20,88,2,3,1,'潘2','158110','4#111','2021-06-17 10:24:54',11),

(21,109,2,3,2,'潘金峰','158110','应院4#223','2021-06-17 10:26:06',11),

(22,219,6,4,3,'gyb','1111','33','2021-06-18 09:26:30',12),

(23,79,2,2,1,'潘金峰','158110','应院4#223','2021-06-19 16:52:44',11);

/*Table structure for table `tops` */

DROP TABLE IF EXISTS `tops`;

CREATE TABLE `tops` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) DEFAULT NULL COMMENT '推荐类型(1条幅/2大图/3小图)',
  `good_id` int(11) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8 COMMENT='首页推荐商品';

/*Data for the table `tops` */

insert  into `tops`(`id`,`type`,`good_id`) values 

(50,2,41),

(51,2,38),

(52,2,49),

(53,2,47),

(54,2,17),

(55,2,21),

(56,3,20),

(57,3,19),

(58,3,33),

(59,3,30),

(61,3,46),

(63,3,48),

(64,3,51),

(66,2,18),

(67,2,25),

(78,1,28),

(184,3,56);

/*Table structure for table `types` */

DROP TABLE IF EXISTS `types`;

CREATE TABLE `types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `types` */

insert  into `types`(`id`,`name`) values 

(1,'进口食品'),

(2,'家庭清洁'),

(3,'美容洗护'),

(4,'粮油副食'),

(5,'食品饮料');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `name` varchar(255) DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) DEFAULT NULL COMMENT '收货电话',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`password`,`name`,`phone`,`address`) values 

(1,'liu','HAMVRZRssPCADKqGjGWJtQ==','','12323123123',''),

(11,'pan','VbkbA/vOpMyFF03AX62B8A==','潘金峰','158110','应院4#223'),

(12,'gao','CK2bJgsivIO/Q5AC0FM3OA==','gyb','1111','33');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
