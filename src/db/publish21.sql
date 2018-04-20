-- 重新建表
DROP TABLE IF EXISTS `jc_book_message`;
DROP TABLE IF EXISTS `jc_ceb_limit`;
DROP TABLE IF EXISTS `jc_issue`;
DROP TABLE IF EXISTS `jc_borrow`;
DROP TABLE IF EXISTS `jc_book`;

DROP TABLE IF EXISTS `jc_file_obj`;
DROP TABLE IF EXISTS `jc_file`;
DROP TABLE IF EXISTS `jc_file_obj`;
DROP TABLE IF EXISTS `jc_filecenter`; 

-- ----------------------------
-- Table structure for `书目信息表`
-- ----------------------------
DROP TABLE IF EXISTS `jc_book`;
CREATE TABLE `jc_book` (
  `bookid` varchar(36) NOT NULL COMMENT '图书基本信息编号',
  `orgid` varchar(100) DEFAULT  NULL COMMENT '出版机构编号',
  `sourceid` bigint(11) DEFAULT  NULL COMMENT '社内编号',
  `isbn` varchar(50) DEFAULT  NULL COMMENT 'ISBN',
  `title` varchar(50) NOT NULL COMMENT '正书名',
  `parallel_title` varchar(50) DEFAULT  NULL COMMENT '并列书名',
  `subtitle` varchar(50) DEFAULT  NULL COMMENT '副书名',
  `introduce_title` varchar(50) DEFAULT  NULL COMMENT '引进版图书原书名',
  `set_num` tinyint(4) DEFAULT '0' COMMENT '丛套书编号',  
  `set_title` varchar(50) DEFAULT  NULL COMMENT '丛套书名',
  `set_author` varchar(50) DEFAULT  NULL COMMENT '丛套书作者', 
  `independent` tinyint(2) DEFAULT '0' COMMENT '题名是否独立',  
  `author` varchar(100) DEFAULT  NULL COMMENT '作者',
  `author_intro` varchar(500) DEFAULT  NULL  COMMENT '作者简介',
  `reditor` varchar(100) DEFAULT  NULL COMMENT '责任编辑',
  `translator` varchar(50) DEFAULT  NULL COMMENT '译者',
  `editor` varchar(50) DEFAULT  NULL COMMENT '编者',
  `topic_record_num` varchar(20) DEFAULT  NULL COMMENT '选题备案文号',
  `copyright_num` varchar(20) DEFAULT  NULL COMMENT '版权登记号',
  `textbook_num` varchar(20) DEFAULT  NULL COMMENT '教材征订号',
  `content` varchar(200)  DEFAULT  NULL COMMENT '内容提要',
  `language` varchar(3) DEFAULT  NULL COMMENT '正文语种',
  `words` int(10) DEFAULT '0' COMMENT '字数（千字）',
  `version` varchar(50) DEFAULT '0' COMMENT '版次', 
  `price` double(8,2)  default '0.00' COMMENT '定价',
  `price_unit` varchar(2) DEFAULT '0' COMMENT '价格单位', 
  `pages` int(20) DEFAULT '0' COMMENT '页数',
  `length` double(10,2)  default '0.00' COMMENT '长度',
  `length_unit` varchar(2) DEFAULT  NULL COMMENT '长度单位',
  `width` double(10,2)  default '0.00' COMMENT '宽度',
  `width_unit` varchar(2) DEFAULT  NULL COMMENT '宽度单位',
  `height` double(10,2)  default '0.00' COMMENT '高度',
  `height_unit` varchar(2) DEFAULT  NULL COMMENT '高度单位',
  `weigth` double(10,2)  default '0.00' COMMENT '重量',
  `weigth_unit` varchar(2) DEFAULT  NULL COMMENT '重量单位',
  `library_type` varchar(36) DEFAULT  NULL COMMENT '中图法分类',
  `sale_type` varchar(20) DEFAULT  NULL COMMENT '营销分类',
  `reader` varchar(2) DEFAULT  NULL COMMENT '读者对象',
  `keywords` varchar(20) DEFAULT  NULL COMMENT '关键词',
  `topic_words` varchar(20) DEFAULT  NULL COMMENT '主题词',
  `cooperation_publisher` varchar(50) DEFAULT  NULL COMMENT '合作出版者',
  `publish_city` varchar(50) DEFAULT  NULL COMMENT '出版地',
  `publish_country` varchar(50) DEFAULT  NULL COMMENT '出版国',
  `publish_date` date DEFAULT  NULL COMMENT '出版日期',
  `designer` varchar(50) DEFAULT  NULL COMMENT '封面设计者',
  `illustrations` int(20) DEFAULT '0' COMMENT '图幅数',
  `publish_status` varchar(10) DEFAULT NULL COMMENT '出版状态',
  `persion` varchar(36) DEFAULT  NULL COMMENT '联系人',
  `email` varchar(30) DEFAULT  NULL COMMENT '联系邮箱',
  `form` varchar(2) DEFAULT  NULL COMMENT '产品形式',
  `form_detail` varchar(4) DEFAULT  NULL COMMENT '产品形式细节',
  `content_type` varchar(2) DEFAULT  NULL COMMENT '内容类型',
  `system` varchar(2) DEFAULT  NULL COMMENT '操作系统',
  `protection` varchar(2) DEFAULT  NULL COMMENT '保护技术',
  `size`  int(10) DEFAULT '0'  COMMENT '文件大小',
  `size_unit` varchar(2) DEFAULT  NULL COMMENT '文件大小单位',
  `cover` varchar(50) DEFAULT  NULL COMMENT '封底',
  `backcover` varchar(50) DEFAULT  NULL COMMENT '封底',
  `catalogue_page` varchar(50) DEFAULT  NULL COMMENT '目录页',
  `copyright_page` varchar(50) DEFAULT  NULL COMMENT '版权页',
  `cnonix` longtext  DEFAULT NULL COMMENT '书目CNONIX',
  `displayorder` double(12,2)  default '0.00' COMMENT '排序码',
  `thick` double(10,0) DEFAULT NULL,
  `thick_unit` varchar(2) DEFAULT NULL,
  `onixPath` varchar(255) DEFAULT NULL,
  `onixText` text,
   PRIMARY KEY  (`bookid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='书目信息表';


-- ----------------------------
-- Table structure for `电子书使用限制表`
-- ----------------------------
DROP TABLE IF EXISTS `jc_ceb_limit`;
CREATE TABLE `jc_ceb_limit` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `bookid` varchar(36) NOT NULL COMMENT '图书编号',
  `usage_type` varchar(10) DEFAULT  NULL COMMENT '使用类型',
  `usage_status` varchar(10) DEFAULT  NULL COMMENT '使用状态',
  `usage_parts`  int(8) DEFAULT  NULL  COMMENT '使用份数',
  `usage_days`   int(8) DEFAULT  NULL  COMMENT '使用天数',
  `usage_times` int(8) DEFAULT  NULL  COMMENT '使用次数',
  `usage_pages` int(8) DEFAULT  NULL  COMMENT '使用页数',
  PRIMARY KEY (`id`),
  KEY `index_jc_ceb_limit_book` (`bookid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='电子书使用限制表';

-- ----------------------------
-- Table structure for `图书销售信息`
-- ----------------------------
-- DROP TABLE IF EXISTS `jc_book_sale`;
-- CREATE TABLE `jc_book_sale` (
--   `id` bigint(11) NOT NULL AUTO_INCREMENT,
--   `book_id` bigint(11) DEFAULT  NULL COMMENT '图书ID',
--   `sale_right_type` varchar(50) DEFAULT  NULL COMMENT '销售权利类型',
--   `include_country` varchar(50) DEFAULT  NULL COMMENT '包含国家',
--   `include_area`   varchar(50) DEFAULT  NULL COMMENT '包含地区',
--   `uninclude_country` varchar(50) DEFAULT  NULL COMMENT '不包含国家',
--   `uninclude_area`   varchar(50) DEFAULT  NULL COMMENT '不包含地区',
--   `sale_limit_type` varchar(50) DEFAULT  NULL COMMENT '销售限制类型',
--   `sale_place` varchar(100) DEFAULT  NULL COMMENT '销售网点名称',
--   `sale_limit_des` varchar(150) DEFAULT  NULL COMMENT '销售限制描述',
--   `start_date` date DEFAULT  NULL COMMENT '开始日期',
--   `end_date` date DEFAULT  NULL COMMENT '结束日期',
--   PRIMARY KEY (`id`),
--   KEY `index_jc_book_sale_book` (`book_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='图书销售信息';



-- ----------------------------
-- Table structure for `图书借阅表`
-- ----------------------------
DROP TABLE IF EXISTS `jc_borrow`;
CREATE TABLE `jc_borrow` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `bookid` varchar(36) NOT NULL COMMENT '图书编号',
  `orgid` varchar(100) NOT NULL COMMENT '机构编号',
  `times` int(11) DEFAULT '0' COMMENT '借阅次数',
  `date` datetime DEFAULT  NULL COMMENT '借阅日期',
  `status` tinyint(4) DEFAULT '0' COMMENT '发送状态(0:默认值;1:已发送;2:未发送)',
  PRIMARY KEY (`id`),
  KEY `index_jc_borrow_book` (`bookid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='图书借阅表';


-- ----------------------------
-- Table structure for `图书发行信息表`
-- ----------------------------
DROP TABLE IF EXISTS `jc_issue`;
CREATE TABLE `jc_issue` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `bookid` varchar(36) NOT NULL COMMENT '图书编号',
  `orgid` varchar(100) NOT NULL COMMENT '机构编号',
  `orders` int(11) DEFAULT '0' COMMENT '采购数量',
  `sale` int(11) DEFAULT '0' COMMENT '销售数量',
  `invertory` int(11) DEFAULT '0' COMMENT '库存数量',
  `date` datetime DEFAULT  NULL COMMENT '发行时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '发送状态(0:默认值;1:已发送;2:未发送)',
  PRIMARY KEY (`id`),
  KEY `index_jc_issue_book` (`bookid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='图书发行信息表';


-- ----------------------------
-- Table structure for `消息表`
-- ----------------------------
DROP TABLE IF EXISTS `jc_message`;
CREATE TABLE `jc_message` (
  `id` bigint(11) NOT NULL auto_increment,
  `sender` varchar(50) DEFAULT  NULL COMMENT '发送者',
  `receiver` varchar(500) DEFAULT  NULL COMMENT '接收者',
  `sendtime` datetime  DEFAULT  NULL COMMENT '发送时间',
  `type` tinyint(11)  default '0' COMMENT '消息类型(0:默认值;1:发送;2:接收)',
  `memo` longtext  DEFAULT  NULL COMMENT '备注',
  `content` longtext   COMMENT '消息文本',
   PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Table structure for `消息图书映射表`
-- ----------------------------
DROP TABLE IF EXISTS `jc_book_message`;
CREATE TABLE `jc_book_message` (
  `messageid` bigint(11) NOT NULL,
  `bookid` varchar(36) NOT NULL,
   PRIMARY KEY  (`messageid`,`bookid`),
   KEY `index_jc_book_message` (`messageid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书和消息中间表';


-- ----------------------------
-- Table structure for `文件表`
-- ----------------------------
DROP TABLE IF EXISTS `jc_file`;
CREATE TABLE `jc_file` (
  `id` bigint(11) NOT NULL auto_increment,
  `uuid` varchar(36) default NULL COMMENT '附件唯一编号',
  `name` varchar(50) default NULL COMMENT '文件名称',
  `description` varchar(100) DEFAULT  NULL COMMENT '文件描述',
  `path` varchar(100) DEFAULT NULL COMMENT '文件位置',
  `size` bigint(20) DEFAULT NULL default '0' COMMENT '文件大小',
  `type` tinyint(4) DEFAULT '0' COMMENT '附件类型(5:图书其它属性;6:图书消息发送属性)',
  `md5` varchar(100) DEFAULT  NULL COMMENT 'MD5',
  `datatime` datetime DEFAULT  NULL COMMENT '操作时间',
   PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文件表';


-- ----------------------------
-- Table structure for `文件中间表`
-- ----------------------------
 DROP TABLE IF EXISTS `jc_file_obj`;
 CREATE TABLE `jc_file_obj` (
   `id` bigint(11) NOT NULL auto_increment,
   `fileid` bigint(11) NOT NULL COMMENT '文件编号',
   `objid` varchar(36) NOT NULL COMMENT '关联对象编号',
   `objecttype` tinyint(4) NOT NULL default '0' COMMENT '关联对象编号(0:默认值;1:图书;2:消息;)',
    PRIMARY KEY  (`id`),
    KEY `index_jc_file_obj` (`fileid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件中间表';

 
 -- ----------------------------
-- Table structure for `中心文件表`
-- ----------------------------
DROP TABLE IF EXISTS `jc_filecenter`;
CREATE TABLE `jc_filecenter` (
  `id` bigint(11) NOT NULL auto_increment,
  `uuid` varchar(36) default NULL COMMENT '附件唯一编号',
  `name` varchar(50) NOT NULL COMMENT '文件名称',
  `path` varchar(100) NOT NULL COMMENT '文件位置',
  `size` bigint(20) NOT NULL default '0' COMMENT '文件大小',
  `type` tinyint(4) default '0' COMMENT '附件类型(5:图书其它属性;6:图书消息发送属性)',
  `md5` varchar(100) DEFAULT  NULL COMMENT 'MD5',
  `description` varchar(100) default NULL COMMENT '文件描述',
  `datatime` datetime DEFAULT  NULL COMMENT '操作时间',
   PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='中心文件表';


-- ----------------------------
-- Table structure for CMS稿件表
-- ----------------------------
DROP TABLE IF EXISTS `cms_manuscript`;
CREATE TABLE `cms_manuscript` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL COMMENT '标题',
  `subject` varchar(150) DEFAULT  NULL COMMENT '副标题',
  `newsType` tinyint(4) DEFAULT '0' COMMENT '稿件类型',
  `author` varchar(150) DEFAULT  NULL COMMENT '作者',
  `editor` varchar(255) DEFAULT  NULL COMMENT '责任编辑',
  `content` longtext COMMENT '稿件内容',
  `pubTime` datetime DEFAULT  NULL COMMENT '发布时间',
  `displayorder` double(30,10) DEFAULT  NULL COMMENT '排序码',
  `publishstate` tinyint(4) DEFAULT '0' COMMENT '发布状态 0:未发布，1:已发布',
  `nodeID` bigint(11) NOT NULL DEFAULT '0' COMMENT '栏目ID',
  `masterID` bigint(11) NOT NULL DEFAULT '0' COMMENT '原栏目ID',
  `classID` bigint(11) DEFAULT '0' COMMENT '图书分类',
  `issue_date` datetime DEFAULT  NULL COMMENT '发行时间',
  `url` varchar(255) DEFAULT  NULL COMMENT '链接',
  `hitcount` int(11) DEFAULT '0' COMMENT '点击量',
  `piclinks` varchar(255) DEFAULT  NULL COMMENT '缩略图',
  `posterpic` varchar(255) DEFAULT  NULL COMMENT '海报图',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='CMS稿件表';


-- ----------------------------
-- Table structure for cms_faq
-- ----------------------------
DROP TABLE IF EXISTS `cms_faq`;
CREATE TABLE `cms_faq` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(100)  DEFAULT  NULL COMMENT '标题',
  `content` longtext  DEFAULT  NULL COMMENT '留言内容',
  `pubdate` datetime DEFAULT  NULL COMMENT '发表时间',
  `answer_content` varchar(1000)  DEFAULT  NULL COMMENT '回复内容',
  `answer_time` datetime DEFAULT  NULL COMMENT '回复时间',
  `answer_user` varchar(50)  DEFAULT  NULL COMMENT '回复人',
  `loginname` varchar(50)  DEFAULT  NULL COMMENT '会员发表人，会员登录名',
  `anonymous` varchar(50)  DEFAULT  NULL COMMENT '匿名发表人姓名',
  `email` varchar(50)  DEFAULT  NULL COMMENT '匿名邮箱',
  `phone` varchar(30)  DEFAULT  NULL COMMENT '匿名电话',
  `orgname` varchar(50)  DEFAULT  NULL COMMENT '单位名称',
  `address` varchar(100)  DEFAULT  NULL COMMENT '通讯地址',
  `is_hidden` tinyint(4) DEFAULT '0' COMMENT '是否公开提问信息 0:初始值，1:公开，2:不公开',
  `auditor` varchar(50)  DEFAULT  NULL COMMENT '审核人',
  `check_time` datetime DEFAULT  NULL COMMENT '审核时间',
  `audit_status` tinyint(4)  DEFAULT '0' COMMENT '审核状态 0:未审核，1:待审核，2:已审核',
  `ip` varchar(50)  DEFAULT  NULL COMMENT 'IP地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='留言板';


-- ----------------------------
-- Table structure for 软件测试_申请表
-- ----------------------------
DROP TABLE IF EXISTS `jc_test_apply`;
CREATE TABLE `jc_test_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` longtext  DEFAULT NULL COMMENT '软件基本信息',
  `orgid` varchar(100) NOT NULL COMMENT '机构编号',
  `create_time` datetime DEFAULT  NULL COMMENT '创建时间',
  `status` tinyint(4)  DEFAULT '0' COMMENT '状态 0:待审核，1:审核通过，2:未通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='软件测试_申请表';


-- ----------------------------
-- Table structure for 软件测试_流程表
-- ----------------------------
DROP TABLE IF EXISTS `jc_test_process`;
CREATE TABLE `jc_test_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `appplyid` varchar(36) DEFAULT NULL COMMENT '申请编号',
  `flowid` varchar(36) DEFAULT NULL COMMENT '工作流编号',
  `nodeid` varchar(36) DEFAULT NULL COMMENT '节点编号',
  `nodename` varchar(50) DEFAULT NULL COMMENT '节点名称',
  `lastnodeid` varchar(36) DEFAULT NULL COMMENT '前置节点',
  `nextnodeid` varchar(36) DEFAULT NULL COMMENT '后置节点',
  `operate` varchar(50) DEFAULT NULL COMMENT '操作人登录名',
  `operatorname` varchar(50) DEFAULT NULL COMMENT '操作人姓名',
  `begintime` datetime DEFAULT  NULL COMMENT '开始时间',
  `endtime` datetime DEFAULT  NULL COMMENT '结束时间',
  `status` tinyint(4)  DEFAULT '0' COMMENT '状态 0:待审核，1:审核通过，2:未通过',
  `memo` longtext  DEFAULT  NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='软件测试_流程表';


-- ----------------------------
-- Table structure for 数据符合性测试_测试任务表
-- ----------------------------
DROP TABLE IF EXISTS `jc_test_task`;
CREATE TABLE `jc_test_match` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(50) NOT NULL COMMENT '正书名',
  `author` varchar(100) DEFAULT  NULL COMMENT '作者',
  `isbn` varchar(50) DEFAULT  NULL COMMENT 'ISBN',
  `version` varchar(50) DEFAULT '0' COMMENT '版次', 
  `price` double(8,2)  default '0.00' COMMENT '定价',
  `operatetime` datetime DEFAULT  NULL COMMENT '操作时间',
  `status` tinyint(4)  DEFAULT '0' COMMENT '状态 0:待审核，1:审核通过，2:未通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='数据符合性测试_测试任务表';


-- ----------------------------
-- Table structure for meta_class
-- ----------------------------
-- DROP TABLE IF EXISTS `meta_class`;
-- CREATE TABLE `meta_class` (
--  `class_id` char(36) NOT NULL,
--  `class_code` varchar(255) NOT NULL,
--  `class_name` varchar(255) NOT NULL,
--  `parent_class_id` char(36) DEFAULT  NULL,
--  `display_order` decimal(18,10) NOT NULL,
--  PRIMARY KEY (`class_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='分类表';



-- 下面代码是，为各个表添加外键

-- ------------------------------------------------------------------------------------
-- 图书消息中间表
-- ------------------------------------------------------------------------------------
-- ALTER TABLE `jc_book_message`
-- ADD CONSTRAINT `fk_jc_book_message_b` FOREIGN KEY (`bookid`) REFERENCES `jc_book` (`bookid`),
-- ADD CONSTRAINT `fk_jc_book_message_m` FOREIGN KEY (`messageid`) REFERENCES `jc_message` (`id`);


-- ------------------------------------------------------------------------------------
-- 消息表
-- ------------------------------------------------------------------------------------
ALTER TABLE `jc_message`
ADD CONSTRAINT `fk_jc_message_sender` FOREIGN KEY (`sender`) REFERENCES `security_sys_organization` (`orgCode`);


-- ------------------------------------------------------------------------------------
-- 借阅表
-- ------------------------------------------------------------------------------------
ALTER TABLE `jc_borrow`
-- 借阅表关联机构表(主外键)
ADD CONSTRAINT `fk_jc_borrow_organization_code` FOREIGN KEY (`orgid`) REFERENCES `security_sys_organization` (`orgCode`),
-- 借阅表关联图书表(主外键)
ADD CONSTRAINT `fk_jc_borrow_book_id` FOREIGN KEY (`bookid`) REFERENCES `jc_book` (`bookid`);


-- ------------------------------------------------------------------------------------
-- 发行表
-- ------------------------------------------------------------------------------------
ALTER TABLE `jc_issue`
-- 发行表关联机构表(主外键)
ADD CONSTRAINT `fk_jc_issue_organization_code` FOREIGN KEY (`orgid`) REFERENCES `security_sys_organization` (`orgCode`),
-- 发行表关联图书表(主外键)
ADD CONSTRAINT `fk_jc_issue_book_id` FOREIGN KEY (`bookid`) REFERENCES `jc_book` (`bookid`);

-- ------------------------------------------------------------------------------------
-- 电子书限制表
-- ------------------------------------------------------------------------------------
ALTER TABLE `jc_ceb_limit`
ADD CONSTRAINT `fk_jc_ceb_limit_book_id` FOREIGN KEY (`bookid`) REFERENCES `jc_book` (`bookid`);

-- ALTER TABLE `jc_book_sale`
-- 电子书表@图书表
-- ADD CONSTRAINT `fk_jc_book_sale_book_id` FOREIGN KEY (`bookid`) REFERENCES `jc_book` (`id`);

  



  
 