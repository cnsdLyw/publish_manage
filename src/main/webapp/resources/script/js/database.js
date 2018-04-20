var tables = [ {
	"text" : "图书",
	"value" : "book",
	"tables" : [ 
	    {"tableName" : "jc_book_entity","text" : "图书表","comment" : "图书基本信息表"},
	    {"tableName" : "jc_book_org","text": "机构图书表","comment" : "保存图书和机构信息"},
	    {"tableName" : "jc_book_version","text" : "图书版本表","comment" : "保存图书版本信息"}, 
	    {"tableName" : "jc_ceb_limit","text" : "电子书使用限制表","comment" : "保存图书的电子书限制信息"}, 
	    {"tableName" : "jc_book_gather_borrow","text" : "图书借阅数据表","comment" : "保存图书借阅信息"},
	    {"tableName" : "jc_book_gather_copy","text" : "图书副本数据表","comment" : "保存图书副本信息"},
	    {"tableName" : "jc_book_gather_order","text" : "图书订购数据表","comment" : "保存图书订购数据"},
	    {"tableName" : "jc_book_gather_reserve","text" : "图书预定数据表","comment" : "保存图书预定数据"},
	    {"tableName" : "jc_book_gather_return","text" : "图书退货数据表","comment" : "保存图书退货数据"},
	    {"tableName" : "jc_book_gather_sale","text" : "图书销售数据表","comment" : "保存图书销售数据"},
	    {"tableName" : "jc_book_gather_stock","text" : "图书库存数据表","comment" : "保存图书库存数据"},
		{"tableName" : "jc_book_product_supply","text" : "产品供应表","comment" : "保存图书产品供应信息"}, 
		{"tableName" : "jc_book_history","text" : "图书发送记录表","comment" : "保存图书每次发送的详细Product信息"}, 
		{"tableName" : "jc_message","text" : "消息表","comment" : "保存图书发送的消息记录"},
		{"tableName" : "jc_book_message","text" : "图书消息表","comment" : "保存图书和消息的关联信息"},
		{"tableName" : "jc_file_main","text" : "附件信息表","comment" : "保存附件信息"}
	]
 }, {
	"text" : "图书加工",
	"value" : "process",
	"tables" : [ 
	    {"tableName" : "process_task","text" : "加工任务","comment" : "保存图书加工任务"}, 
	    {"tableName" : "process_task_flow","text" : "加工任务工作流","comment" : "保存图书加工工作流"} ,
	]
} , {
	"text" : "统计",
	"value" : "statistics",
	"tables" : [ 
	    {"tableName" : "jc_statistics_month","text" : "统计月数据模型","comment" : "保存按月统计数据"}, 
	    {"tableName" : "jc_statistics_year","text" : "统计年数据模型","comment" : "保存按月统计数据"} ,
	    {"tableName" : "jc_statistics_market","text" : "市场统计年数据模型","comment" : "保存市场按月统计数据"} ,
	    {"tableName" : "jc_statistics_market_month","text" : "市场统计年数据模型","comment" : "保存市场按月统计数据"} ,
	    {"tableName" : "jc_statistics_organization","text" : "统计机构数据模型","comment" : "保存按机构统计数据"} ,
	]
} , {
	"text" : "符合性测试",
	"value" : "compliance",
	"tables" : [ 
        {"tableName" : "jc_test_application","text" : "软件符合性测试表","comment" : "保存图书加工任务"}, 
        {"tableName" : "jc_test_case","text" : "符合性测试用例","comment" : "保存符合性测试用例"}, 
        {"tableName" : "jc_data_compliance_test","text" : "数据符合性测试表","comment" : "保存数据符合性测试文件信息"}, 
	    {"tableName" : "compliancetest_task","text" : "符合性测试任务","comment" : "符合性测试任务"}, 
	    {"tableName" : "compliancetest_task_flow" ,"text" : "符合性测试任务工作流","comment" : "符合性测试任务工作流"} 
	]
}, {
	"text" : "图书数据发布",
	"value" : "publish",
	"tables" : [ 
	    {"tableName" : "jc_publish_bookWarehouse","text" : "发布平台图书仓库","comment" : "保存发送交换的图书信息，用以生成图书榜单、热门出版社和作者信息"}, 
	    {"tableName" : "jc_publish_book","text" : "发布平台图书榜单表","comment" : "保存新书畅销榜、图书畅销榜和图书借阅榜数据"} ,
	    {"tableName" : "jc_publish_press","text" : "热门出版社表","comment" : "保存热门出版社"}, 
	    {"tableName" : "jc_publish_author","text" : "热门作者表","comment" : "保存热门作者"}
	]
}, {
	"text" : "信息发布",
	"value" : "cms",
	"tables" : [ 
	    {"tableName" : "cms_faq","text" : "留言板","comment" : "保存留言信息"}, 
	    {"tableName" : "cms_manuscript","text" : "CMS稿件表","comment" : "保存首页大图、行业信息、友情链接和文件发布信息"} ,
	    {"tableName" : "jc_publish_comment","text" : "图书书评表","comment" : "保存书评信息"} 
	]
}, {
	"text" : "工作流",
	"value" : "workFlow",
	"tables" : [ 
	    {"tableName" : "sys_workflow","text" : "工作流","comment" : "保存工作流"}, 
	    {"tableName" : "sys_workflow_node","text" : "工作流节点","comment" : "保存工作流下的节点信息"}, 
	    {"tableName" : "sys_workflow_transfer","text" : "工作流流程表","comment" : "保存工作流下的节点流程信息"} 
	]
}, {
	"text" : "系统",
	"value" : "sys",
	"tables" : [ 
        {"tableName" : "security_sys_organization","text" : "系统机构表","comment" : "保存机构信息"}, 
        {"tableName" : "security_sys_organizationapply","text" : "机构申请表","comment" : "保存申请机构信息"}, 
        {"tableName" : "security_sys_organizationlibrary","text" : "标准机构代码表","comment" : "保存标准机构代码"}, 
	    {"tableName" : "security_sys_user","text" : "用户表","comment" : "保存用户信息"}, 
	    {"tableName" : "security_sys_role","text" : "角色表","comment" : "保存角色信息"}, 
		{"tableName" : "security_sys_user_role","text" : "用户-角色表","comment" : "保存用户和角色的关联信息"}, 
		{"tableName" : "security_sys_authority","text" : "权限表","comment" : "保存权限信息"}, 
		{"tableName" : "security_sys_role_authority","text" : "角色-权限表","comment" : "保存角色和权限的关联信息"},
		{"tableName" : "sys_loginfo","text" : "日志信息表","comment" : "描述系统日志信息"},
		//{"tableName" : "jc_classification_type","text" : "系统分类类别表","comment" : "保存系统分类类型"},
		//{"tableName" : "jc_classification","text" : "系统分类表","comment" : "保存系统分类"},
		//{"tableName" : "sys_configure_group","text" : "系统配置分类表","comment" : "保存系统的配置分类"},
		//{"tableName" : "sys_configure","text" : "系统配置表","comment" : "保存系统配置"},
		//{"tableName" : "sys_storage_device","text" : "存储设备表","comment" : "保存ftp信息"}
	]
} ]