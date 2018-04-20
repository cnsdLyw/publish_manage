<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	
%>
<c:set value="admin" var="loginname"></c:set>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<script src="${ctx }/resources/script/js/database.js"></script>    
<!-- ace scripts -->
<script type="text/javascript">
	jQuery(function($) {
		//初始化left页面
		var content = "";
		var liClass = "active open";
		var liTableClass = "active";
		$("#dataLeftUl").html("");
		for (var i = 0; i < tables.length; i++) {
		    var str = tables[i];
		    if(i!=0){
		    	liClass = "";
		    }
		    content+="<li class='"+liClass+"'>"+
						"<a href='#' class='dropdown-toggle'>"+
							"<i class='menu-icon fa fa-database'></i>"+
							"<span class='menu-text'> "+tables[i].text+" </span>"+
							"<b class='arrow fa fa-angle-down'></b>"+
						"</a>"+
					"<b class='arrow'></b>"+
					"<ul class='submenu'>";
		    var tableContent = str.tables;
		    for (var j = 0; j < tableContent.length; j++) {
		    	if(j!=0){
		    		liTableClass = "";
		    	}
		    	content+="<li id='"+tableContent[j].tableName+"_li' class='"+liTableClass+"'>"+
							"<a href=\"javascript:setTable('"+tableContent[j].tableName+"_li','"+tables[i].text+"','"+tableContent[j].tableName+"','"+tableContent[j].text+"','"+tableContent[j].comment+"');\">"+tableContent[j].text+"</a>"+
							"<b class='arrow'></b>"+
						"</li>";
				if(i==0&&j==0){
					setTable(tableContent[j].tableName+"_li",tables[i].text,tableContent[j].tableName,tableContent[j].text,tableContent[j].comment);
				}		
		    }
		    content+="</ul></li>";	
		}
		$("#dataLeftUl").html(content);
	});
</script>
</head>

<body>
	<div id="sidebar" class="sidebar responsive">
		<ul class="nav nav-list" id="dataLeftUl">
		
		</ul>
		<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
			<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
				data-icon2="ace-icon fa fa-angle-double-right"></i>
		</div>
	</div>
</body>
</html>
