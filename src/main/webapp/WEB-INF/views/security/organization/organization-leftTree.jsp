<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>机构列表</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/BootstrapMenu.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
   
</script>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				
					<div id="treeview"></div>
				</div><!--end  main-content-inner -->
			</div><!-- end page-content -->

	</div><!-- end main-container -->
	<%@include file="../../common/javascript.html"%>

	<script src="${ctx }/resources/script/bootstrap/js/fuelux/fuelux.tree-organization.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	//以下是获取当前分类下所有节点
	jQuery(function($){
		var sampleData = initiateDemoData();//see below
		$('#treeview').ace_tree({
			dataSource: sampleData['dataSource'],
			multiSelect: false,
			cacheItems: true,
			'open-icon' : 'ace-icon tree-minus',
			'close-icon' : 'ace-icon tree-plus',
			'selectable' : false,
			'selected-icon' : 'ace-icon fa fa-check',
			'unselected-icon' : 'ace-icon fa fa-times',
			loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
		});
		
		function initiateDemoData(){
			var tree_data ={ };
			/*var tree_data = {'刑侦':{text:'刑侦',type:'folder','additionalParameters':{id:'1','children':{'痕迹检验':{text:'痕迹检验',type:'item','additionalParameters':{id:'10'}},'刑侦光源':{text:'刑侦光源',type:'item','additionalParameters':{id:'11'}},'现场勘察':{text:'现场勘察',type:'item','additionalParameters':{id:'12'}},'反恐处突':{text:'反恐处突',type:'item','additionalParameters':{id:'13'}},'声像技术':{text:'声像技术',type:'item','additionalParameters':{id:'9','item-selected':true}}}}},
			'技侦':{text:'技侦',type:'folder','additionalParameters':{id:'19','children':{'监听':{text:'监听',type:'item','additionalParameters':{id:'20'}},'定位':{text:'定位',type:'item','additionalParameters':{id:'21'}}}}},
			'网监':{text:'网监',type:'folder','additionalParameters':{id:'25','children':{'互联网舆情':{text:'互联网舆情',type:'item','additionalParameters':{id:'26'}},'公共信息网络监控':{text:'公共信息网络监控',type:'item','additionalParameters':{id:'27'}}}}},
			'交警':{text:'交警',type:'folder','additionalParameters':{id:'32','children':{'交通事故':{text:'交通事故',type:'item','additionalParameters':{id:'33'}},'交通道理管理':{text:'交通道理管理',type:'item','additionalParameters':{id:'34'}}}}}};
			*/
			var dataSource = function(options, callback){
				var $data = null
				if(!("text" in options) && !("type" in options)){
					$.ajax( {  
						type : "get",  
						url : "${ctx}/organization/getTreeData/?orgCode=ORG_CLASS",
						dataType:"json",  
						async:false,
						success : function(data) {
							tree_data = data;
						}
					});
					$data = tree_data;//the root tree
					callback({ data: $data });
					return;
				}
				else if("type" in options && options.type == "folder") {
					if("additionalParameters" in options && "children" in options.additionalParameters){
						$data = options.additionalParameters.children || {};
					}else $data = {}//no data
				}
				
				if($data != null){
					setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 500) + 200);
				}
	
			}
			return {'dataSource': dataSource}
		}
		
		/*$("#treeview").on("selected.fu.tree",function(e,data){//e,事件。点击节点
			window.open("${ctx }/jcClassification/updateClass?classkey=${classKey}&classCode=" + data.target.code, "main_content_configure");
		});*/
	
	});
	
	</script>

</body>
</html>