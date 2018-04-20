var columnList = [
    {firstColumnName:'',columnName:'书目中心',id:'1',parentId:"",authorityKey:'',loginRoleS:'1,2,3,4'},
	{firstColumnName:'书目中心',columnName:'出版社书目管理',id:'11',parentId:"1",authorityKey:'',loginRoleS:'1'},
	{firstColumnName:'书目中心',columnName:'图书馆书目管理',id:'12',parentId:"1",authorityKey:'',loginRoleS:'2'},
	{firstColumnName:'书目中心',columnName:'发行书目管理',id:'13',parentId:"1",authorityKey:'',loginRoleS:'3'},
	{firstColumnName:'书目中心',columnName:'中心书目管理',id:'14',parentId:"1",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'书目中心',columnName:'数据交换策略管理',id:'15',parentId:"1",authorityKey:'',loginRoleS:'1,2,3,4'},
	
	{firstColumnName:'',columnName:'消息中心',id:'2',parentId:"",authorityKey:'',loginRoleS:'1,2,3,4'},
	{firstColumnName:'消息中心',columnName:'发信箱',id:'21',parentId:"2",authorityKey:'',loginRoleS:'1,2,3'},
	{firstColumnName:'消息中心',columnName:'收信箱',id:'22',parentId:"2",authorityKey:'',loginRoleS:'1,2,3'},
	{firstColumnName:'消息中心',columnName:'消息管理',id:'23',parentId:"2",authorityKey:'',loginRoleS:'4'},
	

    {firstColumnName:'',columnName:'加工中心',id:'3',parentId:"",authorityKey:'',loginRoleS:'1,4'},
	{firstColumnName:'加工中心',columnName:'加工任务管理',id:'31',parentId:"3",authorityKey:'',loginRoleS:'1'},
	{firstColumnName:'加工中心',columnName:'中心加工管理',id:'32',parentId:"3",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'加工中心',columnName:'我的加工任务',id:'33',parentId:"3",authorityKey:'',loginRoleS:'4'},
	
    {firstColumnName:'',columnName:'加工统计',id:'4',parentId:"",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'加工统计',columnName:'加工数据统计',id:'41',parentId:"4",authorityKey:'',loginRoleS:'4'},
	

    {firstColumnName:'',columnName:'软件符合性测试',id:'5',parentId:"",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'软件符合性测试',columnName:'测试申请管理',id:'51',parentId:"5",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'软件符合性测试',columnName:'测试任务管理',id:'52',parentId:"5",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'软件符合性测试',columnName:'我的测试任务',id:'53',parentId:"5",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'软件符合性测试',columnName:'测试用例管理',id:'54',parentId:"5",authorityKey:'',loginRoleS:'4'},
	

    {firstColumnName:'',columnName:'图书信息发布',id:'6',parentId:"",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'图书信息发布',columnName:'书目发行借阅总量',id:'61',parentId:"6",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'图书信息发布',columnName:'新书畅销榜',id:'62',parentId:"6",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'图书信息发布',columnName:'图书畅销榜',id:'63',parentId:"6",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'图书信息发布',columnName:'图书借阅榜',id:'64',parentId:"6",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'图书信息发布',columnName:'热门作者',id:'65',parentId:"6",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'图书信息发布',columnName:'热门出版社',id:'66',parentId:"6",authorityKey:'',loginRoleS:'4'},
	

    {firstColumnName:'',columnName:'信息发布管理',id:'7',parentId:"",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'信息发布管理',columnName:'首页大图管理',id:'71',parentId:"7",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'信息发布管理',columnName:'行业信息管理',id:'72',parentId:"7",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'信息发布管理',columnName:'友情链接管理',id:'73',parentId:"7",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'信息发布管理',columnName:'文件发布管理',id:'74',parentId:"7",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'信息发布管理',columnName:'留言板管理',id:'75',parentId:"7",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'信息发布管理',columnName:'常见问题管理',id:'76',parentId:"7",authorityKey:'',loginRoleS:'4'},
	

    {firstColumnName:'',columnName:'计费计量',id:'11',parentId:"",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'计费计量',columnName:'计费查询(机构)',id:'111',parentId:"11",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'计费计量',columnName:'计费查询',id:'112',parentId:"11",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'计费计量',columnName:'计费策略',id:'113',parentId:"11",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'计费计量',columnName:'计费权限',id:'114',parentId:"11",authorityKey:'',loginRoleS:'4'},
	
	{firstColumnName:'',columnName:'统计中心',id:'8',parentId:"",authorityKey:'',loginRoleS:'1,2,3'},
	//{firstColumnName:'统计中心',columnName:'统计管理',id:'81',parentId:"8",authorityKey:'',loginRoleS:'1,2,3'},
	{firstColumnName:'统计中心',columnName:'图书检索',id:'82',parentId:"8",authorityKey:'',loginRoleS:'1,2,3'},
	{firstColumnName:'统计中心',columnName:'社内排行榜',id:'83',parentId:"8",authorityKey:'',loginRoleS:'1,2,3'},
	{firstColumnName:'统计中心',columnName:'行业排行榜',id:'84',parentId:"8",authorityKey:'',loginRoleS:'1,2,3'},
	{firstColumnName:'统计中心',columnName:'市场',id:'85',parentId:"8",authorityKey:'',loginRoleS:'1,2,3'},
	
	

    {firstColumnName:'',columnName:'系统管理',id:'9',parentId:"",authorityKey:'',loginRoleS:'1,2,3,4'},
	//{firstColumnName:'系统管理',columnName:'文件管理',id:'91',authorityKey:'',loginRoleS:'1,2,3,4'},
	//{firstColumnName:'系统管理',columnName:'存储设备管理',id:'92',parentId:"9",authorityKey:'',loginRoleS:'1,2,3,4'},
	//{firstColumnName:'系统管理',columnName:'配置管理',id:'93',parentId:"9",authorityKey:'',loginRoleS:'1,2,3,4'},
	{firstColumnName:'系统管理',columnName:'分类管理',id:'94',parentId:"9",authorityKey:'',loginRoleS:'1,2,3,4'},
	{firstColumnName:'系统管理',columnName:'任务管理',id:'95',parentId:"9",authorityKey:'',loginRoleS:'4'},
	//{firstColumnName:'系统管理',columnName:'机构信息',id:'96',parentId:"9",authorityKey:'',loginRoleS:'1,2,3,4'},
	{firstColumnName:'系统管理',columnName:'机构申请管理',id:'97',parentId:"9",authorityKey:'',loginRoleS:'4'},
	//{firstColumnName:'系统管理',columnName:'数据交换管理',id:'98',parentId:"9",authorityKey:'',loginRoleS:'4'},
	//{firstColumnName:'系统管理',columnName:'标准机构代码',id:'99',parentId:"9",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'系统管理',columnName:'机构管理',id:'910',parentId:"9",authorityKey:'',loginRoleS:'1,2,3,4'},
	{firstColumnName:'系统管理',columnName:'用户管理',id:'911',parentId:"9",authorityKey:'',loginRoleS:'1,2,3,4'},
	{firstColumnName:'系统管理',columnName:'角色管理',id:'912',parentId:"9",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'系统管理',columnName:'权限管理',id:'913',parentId:"9",authorityKey:'',loginRoleS:'4'},
	{firstColumnName:'系统管理',columnName:'代码表管理',id:'914',parentId:"9",authorityKey:'',loginRoleS:'4'},
	
    {firstColumnName:'',columnName:'日志管理',id:'10',parentId:"",authorityKey:'',loginRoleS:'1,2,3,4'},
	{firstColumnName:'日志管理',columnName:'日志管理',id:'101',parentId:"10",authorityKey:'',loginRoleS:'1,2,3,4'},
	
];